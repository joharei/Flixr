package no.joharei.flixr.network


import androidx.lifecycle.LiveData
import io.reactivex.Observable
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.network.framework.ApiResponse
import no.joharei.flixr.network.models.Photos
import no.joharei.flixr.network.models.PhotosetsResponse

interface FlickrApiContainer {
    fun getUserDetails(): Observable<User>

    fun getPhotosets(userId: String?): LiveData<ApiResponse<PhotosetsResponse>>

    fun getPhotos(photosetId: Long, userId: String?): Observable<Photos>

    fun getContacts(): Observable<Contacts>
}
