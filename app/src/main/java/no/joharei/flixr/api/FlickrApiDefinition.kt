package no.joharei.flixr.api


import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

internal interface FlickrApiDefinition {

    @GET("?method=flickr.test.login")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    fun getUserDetails(): Observable<User>

    @GET("?method=flickr.photosets.getList&primary_photo_extras=url_n,url_k")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    fun getPhotosets(
            @Query("user_id") userId: String?
    ): Observable<Photosets>

    @GET("?method=flickr.photosets.getPhotos&extras=url_n,url_k")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    fun getPhotos(
            @Query("photoset_id") photosetId: Long,
            @Query("user_id") userId: String?
    ): Observable<Photos>

    @GET("?method=flickr.contacts.getList")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    fun getContacts(): Observable<Contacts>
}
