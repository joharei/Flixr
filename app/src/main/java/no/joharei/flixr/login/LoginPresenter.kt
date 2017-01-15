package no.joharei.flixr.login

import no.joharei.flixr.MainApplication
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.subscriptions.CompositeSubscription
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import javax.inject.Inject

class LoginPresenter : AnkoLogger {
    @Inject
    lateinit var loginApi: LoginApi
    @Inject
    lateinit var oAuthConsumer: OkHttpOAuthConsumer
    @Inject
    lateinit var localCredentialStore: LocalCredentialStore
    lateinit var view: LoginView
    val compositeSubscription = CompositeSubscription()

    fun attachView(view: LoginView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    fun getUserDetails() {
        val authToken = localCredentialStore.token
        oAuthConsumer.setTokenWithSecret(authToken.authToken, authToken.authTokenSecret)
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

    fun stop() {
        compositeSubscription.unsubscribe()
    }
}
