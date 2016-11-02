package no.joharei.flixr.photos;

import android.util.Log;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.preferences.CommonPreferences;
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
        String user = userId != null ? userId : CommonPreferences.getUserNsid(view.getContext());
        photosApi.getPhotos(photosetId, user)
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(photosPhotoset -> {
                    view.showPhotos(photosPhotoset.getPhotos());
                }, throwable -> {
                    Log.e(TAG, "Failed fetching photos", throwable);
                });
    }
}
