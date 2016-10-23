package no.joharei.flixr.api;


import no.joharei.flixr.api.models.PhotosPhotosetContainer;
import no.joharei.flixr.api.models.PhotosetsContainer;
import no.joharei.flixr.login.models.Login;
import no.joharei.flixr.mainpage.models.ContactsContainer;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface FlickrApiDefinition {
    @GET("?method=flickr.test.login")
    Observable<Login> getUserDetails();

    @GET("?method=flickr.photosets.getList")
    Observable<PhotosetsContainer> getPhotosets(
            @Query("user_id") String userId
    );

    @GET("?method=flickr.photosets.getPhotos")
    Observable<PhotosPhotosetContainer> getPhotos(
            @Query("photoset_id") long photosetId,
            @Query("user_id") String userId
    );

    @GET("?method=flickr.contacts.getList")
    Observable<ContactsContainer> getContacts();
}
