package no.joharei.flixr.modules


import dagger.Module

@Module(includes = arrayOf(MainModule::class, AndroidModule::class))
class RootModule
