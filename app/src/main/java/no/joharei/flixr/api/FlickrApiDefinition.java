package no.joharei.flixr.api;


import no.joharei.flixr.api.models.PhotosPhotoset;
import no.joharei.flixr.api.models.Photosets;
import no.joharei.flixr.login.models.User;
import no.joharei.flixr.mainpage.models.Contacts;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface FlickrApiDefinition {
    @GET("?method=flickr.test.login")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    Observable<User> getUserDetails();

    @GET("?method=flickr.photosets.getList")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    Observable<Photosets> getPhotosets(
            @Query("user_id") String userId
    );

    @GET("?method=flickr.photosets.getPhotos")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    Observable<PhotosPhotoset> getPhotos(
            @Query("photoset_id") long photosetId,
            @Query("user_id") String userId
    );

    @GET("?method=flickr.contacts.getList")
    @ResponseEnvelopeConverterFactory.WrappedResponse
    Observable<Contacts> getContacts();
}
