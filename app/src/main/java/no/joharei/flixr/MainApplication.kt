package no.joharei.flixr

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import no.joharei.flixr.di.DaggerMainComponent
import no.joharei.flixr.di.MainComponent
import no.joharei.flixr.logging.AnalyticsCrashReportingTree
import timber.log.Timber

class MainApplication : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(AnalyticsCrashReportingTree)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerMainComponent.builder()
            .application(this)
            .build()
        component.inject(this)
        return component
    }

    companion object {
        lateinit var component: MainComponent
            private set
    }
}
