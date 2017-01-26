package no.joharei.flixr.photos

import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photos
import rx.Observable
import javax.inject.Inject

class PhotosApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer) {
    internal fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> {
        return flickrApiContainer.getPhotos(photosetId, userId)
    }
}
