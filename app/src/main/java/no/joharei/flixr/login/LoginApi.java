package no.joharei.flixr.login;

import javax.inject.Inject;

import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.login.models.Login;
import no.joharei.flixr.tools.ObservableCache;
import rx.Observable;

public class LoginApi {
    private FlickrApiContainer flickrApiContainer;
    private ObservableCache observableCache;

    @Inject
    LoginApi(FlickrApiContainer flickrApiContainer, ObservableCache observableCache) {
        this.flickrApiContainer = flickrApiContainer;
        this.observableCache = observableCache;
    }

    Observable<Login> getUserDetails() {
        return flickrApiContainer.getUserDetails();
    }

    public void clearCache() {
        observableCache.clearCache();
    }
}
