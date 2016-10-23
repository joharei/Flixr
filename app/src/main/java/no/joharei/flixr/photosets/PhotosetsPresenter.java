package no.joharei.flixr.photosets;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.tools.RxAssist;

public class PhotosetsPresenter {
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
                .subscribe(photosetsContainer -> {
                    view.showPhotosets(photosetsContainer.getPhotosets().getPhotoset());
                }, throwable -> {
                    // TODO
                });
    }
}
