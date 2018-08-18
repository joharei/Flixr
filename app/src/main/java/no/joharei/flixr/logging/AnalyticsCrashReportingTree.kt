package no.joharei.flixr.logging

import android.util.Log
import timber.log.Timber

object AnalyticsCrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        AnalyticsCrashLibrary.log(priority, tag, message)
        if (throwable != null) {
            if (priority == Log.ERROR) {
                AnalyticsCrashLibrary.logError(throwable)
            } else if (priority == Log.WARN) {
                AnalyticsCrashLibrary.logWarning(throwable)
            }
        }
    }

}

