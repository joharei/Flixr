package no.joharei.flixr.network


import androidx.lifecycle.LiveData
import io.reactivex.Observable
import no.joharei.flixr.login.models.UserDetailsResponse
import no.joharei.flixr.mainpage.models.ContactsResponse
import no.joharei.flixr.network.framework.ApiResponse
import no.joharei.flixr.network.models.PhotosResponse
import no.joharei.flixr.network.models.PhotosetsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiDefinition {

    @GET("?method=flickr.test.login")
    fun getUserDetails(): Observable<UserDetailsResponse>

    @GET("?method=flickr.photosets.getList&primary_photo_extras=url_n,url_z,url_c,url_b,url_h,url_k,url_o")
    fun getPhotosets(
        @Query("user_id") userId: String?
    ): LiveData<ApiResponse<PhotosetsResponse>>

    @GET("?method=flickr.photosets.getPhotos&extras=url_n,url_z,url_c,url_b,url_h,url_k,url_o")
    fun getPhotos(
        @Query("photoset_id") photosetId: Long,
        @Query("user_id") userId: String?
    ): Observable<PhotosResponse>

    @GET("?method=flickr.contacts.getList")
    fun getContacts(): Observable<ContactsResponse>
}
