package no.joharei.flixr.mainpage

import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class MainApi
@Inject
constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {

    internal fun fetchMyPhotosets(): Observable<Photosets> {
        return observableCache.getCachedObservable(flickrApiContainer.getPhotosets(null), Photosets::class.java, "main", true, true)
    }

    internal fun fetchContacts(): Observable<Contacts> {
        return observableCache.getCachedObservable(flickrApiContainer.getContacts(), Contacts::class.java, true, true)
    }

    internal fun clearCache() = observableCache.clearCache()
}
