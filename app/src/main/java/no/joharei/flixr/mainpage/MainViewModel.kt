package no.joharei.flixr.mainpage

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.common.extensions.toLiveData
import no.joharei.flixr.tools.applyDefaultSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mainApi: MainApi
    private val compositeSubscription = CompositeDisposable()

    fun fetchMyPhotosets() {
        val photosetsSub = mainApi.fetchMyPhotosets()
                .applyDefaultSchedulers()
            .toLiveData {
                Timber.e(it, "Failed fetching my photosets")
            }
//                .subscribe(
//                        { photosets -> view.showMyPhotosets(photosets.photosets) },
//                        { throwable ->
//                            Timber.e(throwable, "Failed fetching my photosets")
//                            mainApi.clearCache()
//                            // TODO
//                        })
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
