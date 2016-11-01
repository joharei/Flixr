package no.joharei.flixr.api;

import no.joharei.flixr.api.models.PhotosPhotoset;
import no.joharei.flixr.api.models.Photosets;
import no.joharei.flixr.login.models.User;
import no.joharei.flixr.mainpage.models.Contacts;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class AndroidFlickrApi implements FlickrApiContainer {

    private FlickrApiDefinition api;

    public AndroidFlickrApi(OkHttpClient okHttpClient, String url) {
        api = createApiClient(okHttpClient, url);
    }

    private FlickrApiDefinition createApiClient(OkHttpClient okHttpClient, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new ResponseEnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(FlickrApiDefinition.class);
    }

    @Override
    public Observable<User> getUserDetails() {
        return api.getUserDetails();
    }

    @Override
    public Observable<Photosets> getPhotosets(String userId) {
        return api.getPhotosets(userId);
    }

    @Override
    public Observable<PhotosPhotoset> getPhotos(long photosetId, String userId) {
        return api.getPhotos(photosetId, userId);
    }

    @Override
    public Observable<Contacts> getContacts() {
        return api.getContacts();
    }
}
