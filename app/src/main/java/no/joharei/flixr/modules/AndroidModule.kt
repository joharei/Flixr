package no.joharei.flixr.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import no.joharei.flixr.MainApplication
import javax.inject.Singleton

/**
 * Module for all Android related provisions
 */

@Module
class AndroidModule(val app: MainApplication) {
    @Provides
    @Singleton
    fun provideApplication(): MainApplication = app

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app

    @Provides
    fun provideDefaultSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
