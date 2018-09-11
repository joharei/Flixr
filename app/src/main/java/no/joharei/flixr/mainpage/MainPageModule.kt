package no.joharei.flixr.mainpage

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import no.joharei.flixr.di.ViewModelKey

@Suppress("unused")
@Module
abstract class MainPageModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindPresenter(presenter: MainViewModel): ViewModel

}