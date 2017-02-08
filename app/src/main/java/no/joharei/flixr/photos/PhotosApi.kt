package no.joharei.flixr.photos

import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class PhotosApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {
    internal fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> {
        return observableCache.getCachedObservable(flickrApiContainer.getPhotos(photosetId, userId), Photos::class.java, "photoset:$photosetId", true, true)
    }

    internal fun clearCache() = observableCache.clearCache()
}
