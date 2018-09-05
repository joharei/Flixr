package no.joharei.flixr.mainpage

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainPageModule {

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}