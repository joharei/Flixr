package no.joharei.flixr.api;


import no.joharei.flixr.api.models.PhotosPhotosetContainer;
import no.joharei.flixr.api.models.PhotosetsContainer;
import no.joharei.flixr.login.models.Login;
import no.joharei.flixr.mainpage.models.ContactsContainer;
import rx.Observable;

public interface FlickrApiContainer {
    Observable<Login> getUserDetails();

    Observable<PhotosetsContainer> getPhotosets(String userId);

    Observable<PhotosPhotosetContainer> getPhotos(long photosetId, String userId);

    Observable<ContactsContainer> getContacts();
}
