package no.joharei.flixr.mainpage;

import javax.inject.Inject;

import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.api.models.Photosets;
import no.joharei.flixr.mainpage.models.Contacts;
import rx.Observable;

public class MainApi {
    private FlickrApiContainer flickrApiContainer;

    @Inject
    MainApi(FlickrApiContainer flickrApiContainer) {
        this.flickrApiContainer = flickrApiContainer;
    }

    Observable<Photosets> getMyPhotosets() {
        return flickrApiContainer.getPhotosets(null);
    }

    Observable<Contacts> getContacts() {
        return flickrApiContainer.getContacts();
    }
}
