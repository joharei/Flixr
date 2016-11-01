package no.joharei.flixr.photosets;

import javax.inject.Inject;

import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.api.models.Photosets;
import rx.Observable;

public class PhotosetsApi {
    private FlickrApiContainer flickrApiContainer;

    @Inject
    PhotosetsApi(FlickrApiContainer flickrApiContainer) {
        this.flickrApiContainer = flickrApiContainer;
    }

    Observable<Photosets> getPhotosets(String userId) {
        return flickrApiContainer.getPhotosets(userId);
    }
}
