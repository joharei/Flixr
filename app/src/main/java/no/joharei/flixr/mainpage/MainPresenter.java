package no.joharei.flixr.mainpage;

import android.util.Log;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.tools.RxAssist;

public class MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
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
                .subscribe(photosets -> {
                    view.showMyPhotosets(photosets.getPhotosets());
                }, throwable -> {
                    Log.e(TAG, "Failed fetching my photosets", throwable);
                    // TODO
                });
    }

    void fetchMyContacts() {
        mainApi.getContacts()
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(contacts -> {
                    view.showMyContacts(contacts.getContacts());
                }, throwable -> {
                    Log.e(TAG, "Failed fetching my contacts", throwable);
                    // TODO
                });
    }
}
