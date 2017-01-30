package no.joharei.flixr.mainpage

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class MainPresenter : AnkoLogger {
    @Inject
    lateinit var mainApi: MainApi
    lateinit var view: MainView
    val compositeSubscription = CompositeDisposable()

    fun attachView(view: MainView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    fun fetchMyPhotosets() {
        val photosetsSub = mainApi.fetchMyPhotosets()
                .applyDefaultSchedulers()
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
                .applyDefaultSchedulers()
                .subscribe(
                        { contacts -> view.showMyContacts(contacts.contacts) },
                        { throwable ->
                            error("Failed fetching my contacts", throwable)
                            // TODO
                        })
        compositeSubscription.add(contactsSub)
    }

    fun stop() {
        compositeSubscription.clear()
    }
}
