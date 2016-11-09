package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import no.joharei.flixr.utils.Constants
import java.util.*

data class Photoset(
        val id: Long = 0,
        val primary: Long = 0,
        val secret: String? = null,
        val server: Int = 0,
        val farm: Int = 0,
        val photos: Int = 0,
        val videos: String? = null,
        @SerializedName("title")
        val titleEnvelope: Title? = null,
        @SerializedName("description")
        val descriptionEnvelope: Description? = null,
        val needsInterstitial: Int = 0,
        val visibilityCanSeeSet: Int = 0,
        val countViews: String? = null,
        val countComments: String? = null,
        val canComment: Int = 0,
        val dateCreate: String? = null,
        val dateUpdate: String? = null
) {
    val title: String get() = titleEnvelope!!.content

    val description: String get() = descriptionEnvelope!!.content

    val cardImageUrl: String
        get() = String.format(Locale.getDefault(),
                Constants.THUMBNAIL_URL_FORMAT,
                farm,
                server,
                primary,
                secret
        )

    val backgroundImageUrl: String
        get() = String.format(Locale.getDefault(),
                Constants.FULLSCREEN_URL_FORMAT,
                farm,
                server,
                primary,
                secret
        )
}
