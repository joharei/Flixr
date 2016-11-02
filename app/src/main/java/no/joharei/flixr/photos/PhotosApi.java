package no.joharei.flixr.photos;

import javax.inject.Inject;

import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.api.models.PhotosPhotoset;
import rx.Observable;

public class PhotosApi {
    private FlickrApiContainer flickrApiContainer;

    @Inject
    PhotosApi(FlickrApiContainer flickrApiContainer) {
        this.flickrApiContainer = flickrApiContainer;
    }

    Observable<PhotosPhotoset> getPhotos(long photosetId, String userId) {
        return flickrApiContainer.getPhotos(photosetId, userId);
    }
}