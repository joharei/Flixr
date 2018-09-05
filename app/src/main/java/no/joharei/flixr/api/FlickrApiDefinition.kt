package no.joharei.flixr.api


import io.reactivex.Observable
import no.joharei.flixr.api.models.PhotosResponse
import no.joharei.flixr.api.models.PhotosetsResponse
import no.joharei.flixr.login.models.UserDetailsResponse
import no.joharei.flixr.mainpage.models.ContactsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FlickrApiDefinition {

    @GET("?method=flickr.test.login")
    fun getUserDetails(): Observable<UserDetailsResponse>

    @GET("?method=flickr.photosets.getList&primary_photo_extras=url_n,url_z,url_c,url_b,url_h,url_k,url_o")
    fun getPhotosets(
        @Query("user_id") userId: String?
    ): Observable<PhotosetsResponse>

    @GET("?method=flickr.photosets.getPhotos&extras=url_n,url_z,url_c,url_b,url_h,url_k,url_o")
    fun getPhotos(
        @Query("photoset_id") photosetId: Long,
        @Query("user_id") userId: String?
    ): Observable<PhotosResponse>

    @GET("?method=flickr.contacts.getList")
    fun getContacts(): Observable<ContactsResponse>
}
