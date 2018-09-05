package no.joharei.flixr.di

import dagger.Module
import no.joharei.flixr.mainpage.MainPageModule

@Module(
    includes = [
        MainPageModule::class
    ]
)
abstract class ActivityBindingModule