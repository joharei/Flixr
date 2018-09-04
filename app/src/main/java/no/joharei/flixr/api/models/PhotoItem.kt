package no.joharei.flixr.api.models

import android.graphics.Point
import android.os.Parcelable
import android.util.Size
import com.github.awanishraj.aspectratiorecycler.DimInterface
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import no.joharei.flixr.common.getSizeOfSmallestPhotoToFillSize
import no.joharei.flixr.common.getUrlOfSmallestPhotoToFillSize
import no.joharei.flixr.common.getUrlsOfLargerPhotos


sealed class PhotoItem : DimInterface {
    abstract val thumbnailWidth: Int
    abstract val thumbnailHeight: Int
    abstract val widths: List<Int>
    abstract val heights: List<Int>
    abstract val urls: List<String>

    fun photoUrl(width: Int, height: Int): String =
        getUrlOfSmallestPhotoToFillSize(width, height, widths, heights, urls)

    fun getSizeToFill(
        fillWidth: Int = Int.MAX_VALUE,
        fillHeight: Int = Int.MAX_VALUE
    ): Size = getSizeOfSmallestPhotoToFillSize(fillWidth, fillHeight, widths, heights)

    var uIWidth: Int = 0
        get() = if (field == 0) thumbnailWidth else field
    var uIHeight: Int = 0
        get() = if (field == 0) thumbnailHeight else field

    override fun getWidth() = uIWidth
    override fun setWidth(width: Int) {
        uIWidth = width
    }

    override fun getHeight() = uIHeight
    override fun setHeight(height: Int) {
        uIHeight = height
    }

}

@Parcelize
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
    val urlN: String?,
    val urlZ: String?,
    val urlC: String?,
    val urlB: String?,
    val urlH: String?,
    val urlK: String?,
    val urlO: String?
) : PhotoItem(), Parcelable {
    override val thumbnailWidth get() = widthN
    override val thumbnailHeight get() = heightN
    override val widths
        get() = listOfNotNull(widthN, widthZ, widthC, widthB, widthH, widthK, widthO)
    override val heights
        get() = listOfNotNull(heightN, heightZ, heightC, heightB, heightH, heightK, heightO)
    override val urls get() = listOfNotNull(urlN, urlZ, urlC, urlB, urlH, urlK, urlO)

    fun alternativeUrls(width: Int, height: Int) =
        getUrlsOfLargerPhotos(width, height, widths, heights, urls)

}

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
) : PhotoItem() {
    val title: String get() = titleEnvelope.content

    val description: String get() = descriptionEnvelope.content

    override val thumbnailHeight get() = extrasEnvelope.heightN ?: 0

    override val thumbnailWidth get() = extrasEnvelope.widthN ?: 0

    override val widths
        get() = with(extrasEnvelope) {
            listOfNotNull(widthN, widthZ, widthC, widthB, widthH, widthK, widthO)
        }
    override val heights
        get() = with(extrasEnvelope) {
            listOfNotNull(heightN, heightZ, heightC, heightB, heightH, heightK, heightO)
        }
    override val urls
        get() = with(extrasEnvelope) {
            listOfNotNull(urlN, urlZ, urlC, urlB, urlH, urlK, urlO)
        }

    fun backgroundImageUrl(displaySize: Point) =
        getUrlOfSmallestPhotoToFillSize(displaySize.x, displaySize.y, widths, heights, urls)

}
