package no.joharei.flixr.photos;

import android.util.Log;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.tools.RxAssist;

public class PhotosPresenter {
    private static final String TAG = PhotosPresenter.class.getSimpleName();
    @Inject
    PhotosApi photosApi;
    private PhotosView view;

    void attachView(PhotosView view) {
        this.view = view;
        MainApplication.component(view.getContext()).inject(this);
    }

    void fetchPhotos(long photosetId, String userId) {
        photosApi.getPhotos(photosetId, userId)
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(photosPhotosetContainer -> {
                    view.showPhotos(photosPhotosetContainer.getPhotoset().getPhoto());
                }, throwable -> {
                    Log.e(TAG, "Failed fetching photos", throwable);
                });
    }
}
