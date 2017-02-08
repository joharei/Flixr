package no.joharei.flixr.photosets

import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class PhotosetsApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {
    internal fun getPhotosets(userId: String?): Observable<Photosets> {
        return observableCache.getCachedObservable(flickrApiContainer.getPhotosets(userId), Photosets::class.java, "photosets:$userId", true, true)
    }

    internal fun clearCache() = observableCache.clearCache()
}
