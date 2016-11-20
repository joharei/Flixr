package no.joharei.flixr

import dagger.Component
import no.joharei.flixr.login.LoginPresenter
import no.joharei.flixr.mainpage.MainPresenter
import no.joharei.flixr.modules.RootModule
import no.joharei.flixr.photos.PhotosPresenter
import no.joharei.flixr.photosets.PhotosetsPresenter
import javax.inject.Singleton

@Component(modules = arrayOf(RootModule::class))
@Singleton
interface MainComponent {
    fun inject(loginPresenter: LoginPresenter)

    fun inject(mainPresenter: MainPresenter)

    fun inject(photosetsPresenter: PhotosetsPresenter)

    fun inject(photosPresenter: PhotosPresenter)
}
