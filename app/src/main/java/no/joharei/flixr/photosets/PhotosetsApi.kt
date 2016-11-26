package no.joharei.flixr.photosets

import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import rx.Observable
import javax.inject.Inject

class PhotosetsApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer) {
    internal fun getPhotosets(userId: String?): Observable<Photosets> {
        return flickrApiContainer.getPhotosets(userId)
    }
}
