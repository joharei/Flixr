package no.joharei.flixr.mainpage;

import javax.inject.Inject;

import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.api.models.PhotosetsContainer;
import no.joharei.flixr.mainpage.models.ContactsContainer;
import rx.Observable;

public class MainApi {
    private FlickrApiContainer flickrApiContainer;

    @Inject
    MainApi(FlickrApiContainer flickrApiContainer) {
        this.flickrApiContainer = flickrApiContainer;
    }

    Observable<PhotosetsContainer> getMyPhotosets() {
        return flickrApiContainer.getPhotosets(null);
    }

    Observable<ContactsContainer> getContacts() {
        return flickrApiContainer.getContacts();
    }
}
