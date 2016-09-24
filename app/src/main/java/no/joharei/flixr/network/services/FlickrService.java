package no.joharei.flixr.network.services;


import no.joharei.flixr.network.models.ContactsResponse;
import no.joharei.flixr.network.models.Login;
import no.joharei.flixr.network.models.PhotosResponse;
import no.joharei.flixr.network.models.PhotosetsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    @GET("?method=flickr.test.login")
    Call<Login> getLogin();

    @GET("?method=flickr.photosets.getList")
    Call<PhotosetsResponse> getPhotosets(
            @Query("user_id") String userId
    );

    @GET("?method=flickr.photosets.getPhotos")
    Call<PhotosResponse> getPhotos(
            @Query("photoset_id") long photosetId,
            @Query("user_id") String userId
    );

    @GET("?method=flickr.contacts.getList")
    Call<ContactsResponse> getContacts();
}
