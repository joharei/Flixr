package no.joharei.flixr.api


import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import rx.Observable

interface FlickrApiContainer {
    fun getUserDetails(): Observable<User>

    fun getPhotosets(userId: String?): Observable<Photosets>

    fun getPhotos(photosetId: Long, userId: String?): Observable<Photos>

    fun getContacts(): Observable<Contacts>
}
