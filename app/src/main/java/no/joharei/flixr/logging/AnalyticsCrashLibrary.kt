@file:Suppress("UNUSED_PARAMETER")

package no.joharei.flixr.logging

object AnalyticsCrashLibrary {

    fun log(priority: Int, tag: String?, message: String) {
        // TODO add log entry to circular buffer.
    }

    fun logWarning(t: Throwable) {
        // TODO report non-fatal warning.
    }

    fun logError(t: Throwable) {
        // TODO report non-fatal error.
    }
}
