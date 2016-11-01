package no.joharei.flixr.api;


import no.joharei.flixr.api.models.PhotosPhotoset;
import no.joharei.flixr.api.models.Photosets;
import no.joharei.flixr.login.models.User;
import no.joharei.flixr.mainpage.models.Contacts;
import rx.Observable;

public interface FlickrApiContainer {
    Observable<User> getUserDetails();

    Observable<Photosets> getPhotosets(String userId);

    Observable<PhotosPhotoset> getPhotos(long photosetId, String userId);

    Observable<Contacts> getContacts();
}
