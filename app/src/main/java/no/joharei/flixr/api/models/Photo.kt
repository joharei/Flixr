package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Photo(
        val id: Long,
        val secret: String,
        val server: Int,
        val farm: Int,
        val title: String,
        @SerializedName("isprimary")
        val isPrimary: String,
        @SerializedName("ispublic")
        val isPublic: Int,
        @SerializedName("isfriend")
        val isFriend: Int,
        @SerializedName("isfamily")
        val isFamily: Int,
        @SerializedName("width_n")
        val width: Int,
        @SerializedName("height_n")
        val height: Int,
        @SerializedName("url_n")
        val thumbnailUrl: String,
        @SerializedName("url_k")
        val fullscreenImageUrl: String
) : PaperParcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Photo::class.java)
    }
}
