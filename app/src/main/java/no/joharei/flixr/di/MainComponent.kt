package no.joharei.flixr.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import no.joharei.flixr.MainApplication
import no.joharei.flixr.login.LoginPresenter
import no.joharei.flixr.mainpage.MainPresenter
import no.joharei.flixr.photos.PhotosPresenter
import no.joharei.flixr.photosets.PhotosetsPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        NetworkModule::class
    ]
)
interface MainComponent : AndroidInjector<DaggerApplication> {

    fun inject(instance: MainApplication)

    fun inject(loginPresenter: LoginPresenter)

    fun inject(mainPresenter: MainPresenter)

    fun inject(photosetsPresenter: PhotosetsPresenter)

    fun inject(photosPresenter: PhotosPresenter)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MainComponent
    }

}
