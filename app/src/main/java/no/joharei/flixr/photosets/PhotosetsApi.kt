package no.joharei.flixr.photosets

import io.reactivex.Observable
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import javax.inject.Inject

class PhotosetsApi
@Inject
internal constructor(private val flickrApiContainer: FlickrApiContainer) {
    internal fun getPhotosets(userId: String?): Observable<Photosets> {
        return flickrApiContainer.getPhotosets(userId)
    }
}
