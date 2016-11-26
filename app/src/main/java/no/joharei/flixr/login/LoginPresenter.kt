package no.joharei.flixr.login

import no.joharei.flixr.MainApplication
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class LoginPresenter : AnkoLogger {
    @Inject
    lateinit var loginApi: LoginApi
    lateinit var view: LoginView
    val compositeSubscription = CompositeSubscription()

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

    fun stop() {
        compositeSubscription.unsubscribe()
    }
}
