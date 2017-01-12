package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName

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
        val dateUpdate: String,
        @SerializedName("primary_photo_extras")
        val extrasEnvelope: PrimaryPhotoExtras
) {
    val title: String get() = titleEnvelope.content

    val description: String get() = descriptionEnvelope.content

    val thumbnailUrl: String get() = extrasEnvelope.thumbnailUrl

    val backgroundImageUrl: String get() = extrasEnvelope.backgroundImageUrl
}
