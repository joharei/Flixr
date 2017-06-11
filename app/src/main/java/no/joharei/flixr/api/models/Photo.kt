package no.joharei.flixr.api.models

import android.graphics.Point
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import no.joharei.flixr.common.adapters.PhotoItem
import paperparcel.PaperParcel

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
        val widthN: Int,
        val widthZ: Int?,
        val widthC: Int?,
        val widthB: Int?,
        val widthH: Int?,
        val widthK: Int?,
        val widthO: Int?,
        val heightN: Int,
        val heightZ: Int?,
        val heightC: Int?,
        val heightB: Int?,
        val heightH: Int?,
        val heightK: Int?,
        val heightO: Int?,
        val urlN: String,
        val urlZ: String?,
        val urlC: String?,
        val urlB: String?,
        val urlH: String?,
        val urlK: String?,
        val urlO: String?
) : PhotoItem, Parcelable {
    override val thumbnailUrl: String get() = urlN
    override val thumbnailWidth: Int get() = widthN
    override val thumbnailHeight: Int get() = heightN

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let { PaperParcelPhoto.writeToParcel(this, it, flags) }
    }

    override fun describeContents() = 0

    fun fullscreenImageUrl(displaySize: Point): String? {
        val sizes = arrayOf(widthZ, widthC, widthB, widthH, widthK, widthO) zip arrayOf(heightZ, heightC, heightB, heightH, heightK, heightO)
        val index = sizes.indexOfFirst { (it.first != null && it.second != null) && (it.first as Int > displaySize.x || it.second as Int > displaySize.y) }
        val urls = arrayOf(urlZ, urlC, urlB, urlH, urlK, urlO)
        if (index >= 0) {
            return urls[index]
        } else {
            return urls.last { it != null }
        }
    }

    companion object {
        @JvmField val CREATOR = PaperParcelPhoto.CREATOR
    }
}
