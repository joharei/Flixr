package no.joharei.flixr

import android.app.Application
import no.joharei.flixr.modules.AndroidModule

class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        buildComponentAndInject()
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
