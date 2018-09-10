package no.joharei.flixr.mainpage

import io.reactivex.Flowable
import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class MainApi
@Inject
constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {

    fun fetchMyPhotosets(): Flowable<Photosets> = flickrApiContainer.getPhotosets(null)

    fun fetchContacts(): Observable<Contacts> {
        return observableCache.getCachedObservable(flickrApiContainer.getContacts(), Contacts::class.java, true, true)
    }

    fun clearCache() = observableCache.clearCache()
}
