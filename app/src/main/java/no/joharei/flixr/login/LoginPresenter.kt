package no.joharei.flixr.login

import android.net.Uri
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.common.Constants
import no.joharei.flixr.network.LocalCredentialStore
import no.joharei.flixr.network.models.AuthToken
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import oauth.signpost.OAuth
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import timber.log.Timber
import javax.inject.Inject

class LoginPresenter {
    @Inject
    lateinit var loginApi: LoginApi
    @Inject
    lateinit var oAuthConsumer: OkHttpOAuthConsumer
    lateinit var view: LoginView
    private val compositeSubscription = CompositeDisposable()

    fun attachView(view: LoginView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    private fun getUserDetails() {
        val detailsSub = loginApi.fetchUserDetails()
                .applyDefaultSchedulers()
                .subscribe(
                        { user ->
                            view.showProgress(false)
                            CommonPreferences.setUserNsid(view.context, user.id)
                            CommonPreferences.setUsername(view.context, user.username)
                            view.getUserDetailsCompleted()
                        },
                        { throwable -> Timber.e(throwable, "Error getting user details") })
        compositeSubscription.add(detailsSub)
    }

    /**
     * Retrieve the request token, then open a browser for the user to authorize it
     */
    fun retrieveRequestToken(consumer: OAuthConsumer, provider: OAuthProvider) {
        val requestTokenSub = Observable.defer { Observable.just(provider.retrieveRequestToken(consumer, Constants.OAUTH_CALLBACK_URL)) }
                .applyDefaultSchedulers()
                .subscribe(
                        { url ->
                            Timber.i("Popping a browser with the authorize URL : $url")
                            view.loadUrl(url)
                        },
                        { throwable -> Timber.e(throwable, "Error during OAUth retrieve request token") })
        compositeSubscription.add(requestTokenSub)
    }

    /**
     * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
     * for future API calls.
     */
    fun retrieveAccessToken(consumer: OAuthConsumer, provider: OAuthProvider, uri: Uri) {
        val accessTokenSub = Observable.fromCallable {
            val oauthVerifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER)

            provider.retrieveAccessToken(consumer, oauthVerifier)

            val credentialStore = LocalCredentialStore(view.context)
            val authToken = AuthToken(consumer.token, consumer.tokenSecret)
            credentialStore.store(authToken)

            val token = authToken.authToken
            val secret = authToken.authTokenSecret

            oAuthConsumer.setTokenWithSecret(token, secret)
        }
                .applyDefaultSchedulers()
                .subscribe(
                        {
                            Timber.i("OAuth - Access Token Retrieved")
                            getUserDetails()
                        },
                        { throwable -> Timber.e(throwable, "OAuth - Access Token Retrieval Error") })
        compositeSubscription.add(accessTokenSub)
    }

    fun stop() {
        compositeSubscription.clear()
    }
}
