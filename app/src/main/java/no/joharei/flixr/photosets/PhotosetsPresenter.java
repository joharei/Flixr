package no.joharei.flixr.photosets;

import android.util.Log;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.photos.PhotosPresenter;
import no.joharei.flixr.tools.RxAssist;

public class PhotosetsPresenter {
    private static final String TAG = PhotosPresenter.class.getSimpleName();
    @Inject
    PhotosetsApi mainApi;
    private PhotosetsView view;

    void attachView(PhotosetsView view) {
        this.view = view;
        MainApplication.component(view.getContext()).inject(this);
    }

    void fetchPhotosets(String userId) {
        mainApi.getPhotosets(userId)
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(photosets -> {
                    view.showPhotosets(photosets.getPhotosets());
                }, throwable -> {
                    Log.e(TAG, "Failed fetching photosets", throwable);
                    // TODO
                });
    }
}
