package no.joharei.flixr.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.network.FlickrApiContainer
import no.joharei.flixr.network.framework.AppExecutors
import no.joharei.flixr.network.framework.NetworkBoundResult
import no.joharei.flixr.network.framework.Result
import no.joharei.flixr.network.models.Photosets
import no.joharei.flixr.network.models.PhotosetsResponse
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val flickrApiContainer: FlickrApiContainer,
    private val appExecutors: AppExecutors,
    private val observableCache: ObservableCache
) {

    fun fetchMyPhotosets(): LiveData<Result<Photosets>> =
        object : NetworkBoundResult<Photosets, PhotosetsResponse>(appExecutors) {

            override fun saveCallResult(item: PhotosetsResponse) {
                // TODO
            }

            override fun shouldFetch(data: Photosets?) = true

            override fun loadFromDb(): LiveData<Photosets> {
                // TODO
                return MutableLiveData<Photosets>().apply { value = null }
            }

            override fun createCall() =
                flickrApiContainer.getPhotosets(null)

        }.asLiveData()

    fun fetchContacts(): Observable<Contacts> {
        return observableCache.getCachedObservable(
            flickrApiContainer.getContacts(),
            Contacts::class.java,
            true,
            true
        )
    }

    fun clearCache() = observableCache.clearCache()
}
