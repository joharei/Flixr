package no.joharei.flixr.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.network.framework.Result
import no.joharei.flixr.network.models.Photosets
import no.joharei.flixr.tools.applyDefaultSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mainRepository: MainRepository
    private val compositeSubscription = CompositeDisposable()

    fun fetchMyPhotosets(): LiveData<Result<Photosets>> = mainRepository.fetchMyPhotosets()
//            .toLiveData<Photosets> {
//                Timber.e(it, "Failed fetching my photosets")
//            }
//                .subscribe(
//                        { photosets -> view.showMyPhotosets(photosets.photosets) },
//                        { throwable ->
//                            Timber.e(throwable, "Failed fetching my photosets")
//                            mainRepository.clearCache()
//                            // TODO
//                        }

    fun fetchMyContacts() {
        val contactsSub = mainRepository.fetchContacts()
                .applyDefaultSchedulers()
                .subscribe(
                    { contacts -> /*view.showMyContacts(contacts.contacts)*/ },
                        { throwable ->
                            Timber.e(throwable, "Failed fetching my contacts")
                            mainRepository.clearCache()
                            // TODO
                        })
        compositeSubscription.add(contactsSub)
    }

    fun stop() {
        compositeSubscription.clear()
    }
}
