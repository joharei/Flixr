package no.joharei.flixr.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import no.joharei.flixr.MainApplication;

/**
 * Module for all Android related provisions
 */

@Module
public class AndroidModule {
    @Provides
    @Singleton
    Context provideAppContext() {
        return MainApplication.getInstance().getApplicationContext();
    }

    @Provides
    SharedPreferences provideDefaultSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
