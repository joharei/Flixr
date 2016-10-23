package no.joharei.flixr.mainpage;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.tools.RxAssist;

public class MainPresenter {
    @Inject
    MainApi mainApi;
    private MainView view;

    void attachView(MainView view) {
        this.view = view;
        MainApplication.component(view.getContext()).inject(this);
    }

    void fetchMyPhotosets() {
        mainApi.getMyPhotosets()
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(photosetsContainer -> {
                    view.showMyPhotosets(photosetsContainer.getPhotosets().getPhotoset());
                }, throwable -> {
                    // TODO
                });
    }

    void fetchMyContacts() {
        mainApi.getContacts()
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(photosetsContainer -> {
                    view.showMyContacts(photosetsContainer.getContacts().getContact());
                }, throwable -> {
                    // TODO
                });
    }
}
