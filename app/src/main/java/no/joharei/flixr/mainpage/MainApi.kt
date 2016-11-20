package no.joharei.flixr.mainpage

import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.mainpage.models.Contacts
import rx.Observable
import javax.inject.Inject

class MainApi
@Inject
constructor(private val flickrApiContainer: FlickrApiContainer) {

    fun fetchMyPhotosets(): Observable<Photosets> {
        return flickrApiContainer.getPhotosets(null)
    }

    fun fetchContacts(): Observable<Contacts> {
        return flickrApiContainer.getContacts()
    }
}
