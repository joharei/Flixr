package no.joharei.flixr.network.services;


import no.joharei.flixr.network.models.Login;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrService {
    @GET("?method=flickr.test.login")
    Call<Login> getLogin();
}
