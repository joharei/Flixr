package no.joharei.flixr;

import javax.inject.Singleton;

import dagger.Component;
import no.joharei.flixr.login.LoginPresenter;
import no.joharei.flixr.mainpage.MainPresenter;
import no.joharei.flixr.modules.RootModule;
import no.joharei.flixr.photos.PhotosPresenter;
import no.joharei.flixr.photosets.PhotosetsPresenter;

@Component(modules = {RootModule.class})
@Singleton
public interface MainComponent {
    void inject(LoginPresenter loginPresenter);

    void inject(MainPresenter mainPresenter);

    void inject(PhotosetsPresenter photosetsPresenter);

    void inject(PhotosPresenter photosPresenter);
}
