package no.joharei.flixr.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import no.joharei.flixr.mainpage.MainPageModule

@Suppress("unused")
@Module(
    includes = [
        MainPageModule::class
    ]
)
abstract class ActivityBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}