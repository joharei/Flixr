package no.joharei.flixr.api.models

import android.graphics.Point
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
        val thumbnailWidth: Int,
        @SerializedName("height_n")
        val thumbnailHeight: Int,
        @SerializedName("url_n")
        val thumbnailUrl: String,
        val widthZ: Int?,
        val widthC: Int?,
        val widthB: Int?,
        val widthH: Int?,
        val widthK: Int?,
        val widthO: Int?,
        val heightZ: Int?,
        val heightC: Int?,
        val heightB: Int?,
        val heightH: Int?,
        val heightK: Int?,
        val heightO: Int?,
        val urlZ: String?,
        val urlC: String?,
        val urlB: String?,
        val urlH: String?,
        val urlK: String?,
        val urlO: String?
) : PaperParcelable {

    fun fullscreenImageUrl(displaySize: Point): String? {
        val sizes = arrayOf(widthZ, widthC, widthB, widthH, widthK, widthO) zip arrayOf(heightZ, heightC, heightB, heightH, heightK, heightO)
        val index = sizes.indexOfFirst { (it.first != null && it.second != null) && (it.first > displaySize.x || it.second > displaySize.y) }
        val urls = arrayOf(urlZ, urlC, urlB, urlH, urlK, urlO)
        if (index >= 0) {
            return urls[index]
        } else {
            return urls.last { it != null }
        }
    }

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Photo::class.java)
    }
}
