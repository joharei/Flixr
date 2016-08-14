package no.joharei.flixr.network.services;


import no.joharei.flixr.network.models.Login;
import no.joharei.flixr.network.models.PhotosPhotosetContainer;
import no.joharei.flixr.network.models.PhotosetsContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    @GET("?method=flickr.test.login")
    Call<Login> getLogin();

    @GET("?method=flickr.photosets.getList")
    Call<PhotosetsContainer> getPhotosets();

    @GET("?method=flickr.photosets.getPhotos")
    Call<PhotosPhotosetContainer> getPhotos(
            @Query("photoset_id") long photosetId,
            @Query("user_id") String userId
    );
}
