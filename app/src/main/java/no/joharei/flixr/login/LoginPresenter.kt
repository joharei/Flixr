package no.joharei.flixr.login

import android.net.Uri
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.api.models.AuthToken
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import no.joharei.flixr.utils.Constants
import oauth.signpost.OAuth
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import javax.inject.Inject

class LoginPresenter : AnkoLogger {
    @Inject
    lateinit var loginApi: LoginApi
    @Inject
    lateinit var oAuthConsumer: OkHttpOAuthConsumer
    lateinit var view: LoginView
    val compositeSubscription = CompositeDisposable()

    fun attachView(view: LoginView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    fun getUserDetails() {
        val detailsSub = loginApi.fetchUserDetails()
                .applyDefaultSchedulers()
                .subscribe(
                        { user ->
                            view.showProgress(false)
                            CommonPreferences.setUserNsid(view.context, user.id)
                            CommonPreferences.setUsername(view.context, user.username)
                            view.getUserDetailsCompleted()
                        },
                        { throwable -> error("Error getting user details", throwable) })
        compositeSubscription.add(detailsSub)
    }

    /**
     * Retrieve the request token, then open a browser for the user to authorize it
     */
    fun retrieveRequestToken(consumer: OAuthConsumer, provider: OAuthProvider) {
        val requestTokenSub = Observable.defer({ Observable.just(provider.retrieveRequestToken(consumer, Constants.OAUTH_CALLBACK_URL)) })
                .applyDefaultSchedulers()
                .subscribe(
                        { url ->
                            info("Popping a browser with the authorize URL : " + url)
                            view.loadUrl(url)
                        },
                        { throwable -> error("Error during OAUth retrieve request token", throwable) })
        compositeSubscription.add(requestTokenSub)
    }

    /**
     * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
     * for future API calls.
     */
    fun retrieveAccessToken(consumer: OAuthConsumer, provider: OAuthProvider, uri: Uri) {
        val accessTokenSub = Observable.fromCallable({
            val oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER)

            provider.retrieveAccessToken(consumer, oauth_verifier)

            val credentialStore = LocalCredentialStore(view.context)
            val authToken = AuthToken(consumer.token, consumer.tokenSecret)
            credentialStore.store(authToken)

            val token = authToken.authToken
            val secret = authToken.authTokenSecret

            oAuthConsumer.setTokenWithSecret(token, secret)
        })
                .applyDefaultSchedulers()
                .subscribe(
                        {
                            info("OAuth - Access Token Retrieved")
                            getUserDetails()
                        },
                        { throwable -> error("OAuth - Access Token Retrieval Error", throwable) })
        compositeSubscription.add(accessTokenSub)
    }

    fun stop() {
        compositeSubscription.clear()
    }
}
