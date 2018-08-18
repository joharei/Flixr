package no.joharei.flixr

import android.app.Application
import no.joharei.flixr.logging.AnalyticsCrashReportingTree
import no.joharei.flixr.modules.AndroidModule
import timber.log.Timber

class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        buildComponentAndInject()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(AnalyticsCrashReportingTree)
        }
    }

    fun buildComponentAndInject() {
        component = DaggerMainComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }

    companion object {
        lateinit var component: MainComponent
    }
}
