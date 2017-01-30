package no.joharei.flixr.photos

import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photos
import javax.inject.Inject

class PhotosApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer) {
    internal fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> {
        return flickrApiContainer.getPhotos(photosetId, userId)
    }
}
