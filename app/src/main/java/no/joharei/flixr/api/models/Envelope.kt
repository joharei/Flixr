package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Envelope<out T>(
        val stat: String,
        val code: Int,
        val message: String,
        @SerializedName(value = "data",
                alternate = arrayOf("photosets", "photoset", "contacts", "user"))
        val data: T
) {
    class FailureException(stat: String, code: Int, msg: String) : RuntimeException(
            String.format(Locale.getDefault(), "Stat: %s, code: %d, message: %s", stat, code, msg)
    )
}
