package no.joharei.flixr.mainpage

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.tools.applyDefaultSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainPresenter {
    @Inject
    lateinit var mainApi: MainApi
    lateinit var view: MainView
    private val compositeSubscription = CompositeDisposable()

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
                            Timber.e(throwable, "Failed fetching my photosets")
                            mainApi.clearCache()
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
                            Timber.e(throwable, "Failed fetching my contacts")
                            mainApi.clearCache()
                            // TODO
                        })
        compositeSubscription.add(contactsSub)
    }

    fun stop() {
        compositeSubscription.clear()
    }
}
