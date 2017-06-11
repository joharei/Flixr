package no.joharei.flixr.api.models

import android.graphics.Point
import com.google.gson.annotations.SerializedName
import no.joharei.flixr.common.adapters.PhotoItem

data class Photoset(
        val id: Long,
        val primary: Long,
        val secret: String,
        val server: Int,
        val farm: Int,
        val photos: Int,
        val videos: String,
        @SerializedName("title")
        private val titleEnvelope: Title,
        @SerializedName("description")
        private val descriptionEnvelope: Description,
        val needsInterstitial: Int,
        val visibilityCanSeeSet: Int,
        val countViews: String,
        val countComments: String,
        val canComment: Int,
        val dateCreate: String,
        val dateUpdate: String,
        @SerializedName("primary_photo_extras")
        val extrasEnvelope: PrimaryPhotoExtras
) : PhotoItem {
    val title: String get() = titleEnvelope.content

    val description: String get() = descriptionEnvelope.content

    override val thumbnailHeight get() = extrasEnvelope.heightN ?: 0

    override val thumbnailWidth get() = extrasEnvelope.widthN ?: 0

    override fun thumbnailUrl(fillWidth: Int, fillHeight: Int) = extrasEnvelope.backgroundImageUrl(fillWidth, fillHeight)

    fun backgroundImageUrl(displaySize: Point) = extrasEnvelope.backgroundImageUrl(displaySize.x, displaySize.y)
}
