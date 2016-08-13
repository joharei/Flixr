package no.joharei.flixr.network.services;


import no.joharei.flixr.network.models.Login;
import no.joharei.flixr.network.models.PhotosetsContainer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrService {
    @GET("?method=flickr.test.login")
    Call<Login> getLogin();

    @GET("?method=flickr.photosets.getList")
    Call<PhotosetsContainer> getPhotosets();
}
