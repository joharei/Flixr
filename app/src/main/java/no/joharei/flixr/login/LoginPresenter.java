package no.joharei.flixr.login;

import android.util.Log;

import javax.inject.Inject;

import no.joharei.flixr.MainApplication;
import no.joharei.flixr.preferences.CommonPreferences;
import no.joharei.flixr.tools.RxAssist;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    @Inject
    LoginApi loginApi;
    private LoginView view;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    void attachView(LoginView view) {
        this.view = view;
        MainApplication.component(view.getContext()).inject(this);
    }

    void getUserDetails() {
        view.showProgress(true);
        Subscription detailsSub = loginApi.getUserDetails()
                .compose(RxAssist.applyDefaultSchedulers())
                .subscribe(user -> {
                    view.showProgress(false);
                    CommonPreferences.setUserNsid(view.getContext(), user.getId());
                    CommonPreferences.setUsername(view.getContext(), user.getUsername().getContent());
                    view.getUserDetailsCompleted();
                }, throwable -> {
                    Log.e(TAG, "Error getting user details", throwable);
                });
        compositeSubscription.add(detailsSub);
    }

    void stop() {
        compositeSubscription.unsubscribe();
    }
}
