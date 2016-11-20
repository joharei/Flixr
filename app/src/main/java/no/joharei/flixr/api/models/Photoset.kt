package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import no.joharei.flixr.utils.Constants
import java.util.*

data class Photoset(
        val id: Long,
        val primary: Long,
        val secret: String,
        val server: Int,
        val farm: Int,
        val photos: Int,
        val videos: String,
        @SerializedName("title")
        val titleEnvelope: Title,
        @SerializedName("description")
        val descriptionEnvelope: Description,
        val needsInterstitial: Int,
        val visibilityCanSeeSet: Int,
        val countViews: String,
        val countComments: String,
        val canComment: Int,
        val dateCreate: String,
        val dateUpdate: String
) {
    val title: String get() = titleEnvelope.content

    val description: String get() = descriptionEnvelope.content

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
