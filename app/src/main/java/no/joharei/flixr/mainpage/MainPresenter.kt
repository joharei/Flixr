package no.joharei.flixr.mainpage

import no.joharei.flixr.MainApplication
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.tools.RxAssist
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class MainPresenter : AnkoLogger {
    @Inject
    lateinit var mainApi: MainApi
    lateinit var view: MainView
    val compositeSubscription = CompositeSubscription()

    fun attachView(view: MainView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    fun fetchMyPhotosets() {
        val photosetsSub = mainApi.fetchMyPhotosets()
                .compose<Photosets>(RxAssist.applyDefaultSchedulers<Photosets>())
                .subscribe(
                        { photosets -> view.showMyPhotosets(photosets.photosets) },
                        { throwable ->
                            error("Failed fetching my photosets", throwable)
                            // TODO
                        })
        compositeSubscription.add(photosetsSub)
    }

    fun fetchMyContacts() {
        val contactsSub = mainApi.fetchContacts()
                .compose<Contacts>(RxAssist.applyDefaultSchedulers<Contacts>())
                .subscribe(
                        { contacts -> view.showMyContacts(contacts.contacts) },
                        { throwable ->
                            error("Failed fetching my contacts", throwable)
                            // TODO
                        })
        compositeSubscription.add(contactsSub)
    }

    fun stop() {
        compositeSubscription.unsubscribe()
    }
}
