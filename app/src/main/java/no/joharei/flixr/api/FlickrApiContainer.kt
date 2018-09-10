package no.joharei.flixr.api


import io.reactivex.Flowable
import io.reactivex.Observable
import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts

interface FlickrApiContainer {
    fun getUserDetails(): Observable<User>

    fun getPhotosets(userId: String?): Flowable<Photosets>

    fun getPhotos(photosetId: Long, userId: String?): Observable<Photos>

    fun getContacts(): Observable<Contacts>
}
