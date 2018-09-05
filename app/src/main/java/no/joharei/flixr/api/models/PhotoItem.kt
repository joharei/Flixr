package no.joharei.flixr.api.models

import android.graphics.Point
import android.os.Parcelable
import android.util.Size
import com.github.awanishraj.aspectratiorecycler.DimInterface
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
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
@JsonClass(generateAdapter = true)
data class Photo(
    val id: Long,
    val secret: String,
    val server: Int,
    val farm: Int,
    val title: String,
    val isprimary: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int,
    val width_n: Int,
    val width_z: Int?,
    val width_c: Int?,
    val width_b: Int?,
    val width_h: Int?,
    val width_k: Int?,
    val width_o: Int?,
    val height_n: Int,
    val height_z: Int?,
    val height_c: Int?,
    val height_b: Int?,
    val height_h: Int?,
    val height_k: Int?,
    val height_o: Int?,
    val url_n: String?,
    val url_z: String?,
    val url_c: String?,
    val url_b: String?,
    val url_h: String?,
    val url_k: String?,
    val url_o: String?
) : PhotoItem(), Parcelable {
    override val thumbnailWidth get() = width_n
    override val thumbnailHeight get() = height_n
    override val widths
        get() = listOfNotNull(width_n, width_z, width_c, width_b, width_h, width_k, width_o)
    override val heights
        get() = listOfNotNull(height_n, height_z, height_c, height_b, height_h, height_k, height_o)
    override val urls get() = listOfNotNull(url_n, url_z, url_c, url_b, url_h, url_k, url_o)

    fun alternativeUrls(width: Int, height: Int) =
        getUrlsOfLargerPhotos(width, height, widths, heights, urls)

}

@JsonClass(generateAdapter = true)
data class Photoset(
    val id: Long,
    val primary: Long,
    val secret: String,
    val server: Int,
    val farm: Int,
    val photos: Int,
    val videos: String,
    @Json(name = "title")
    val titleEnvelope: Title,
    @Json(name = "description")
    val descriptionEnvelope: Description,
    val needs_interstitial: Int,
    val visibility_can_see_set: Int,
    val count_views: String,
    val count_comments: String,
    val can_comment: Int,
    val date_create: String,
    val date_update: String,
    @Json(name = "primary_photo_extras")
    val extrasEnvelope: PrimaryPhotoExtras
) : PhotoItem() {
    val title: String get() = titleEnvelope.content

    val description: String get() = descriptionEnvelope.content

    override val thumbnailHeight get() = extrasEnvelope.height_n ?: 0

    override val thumbnailWidth get() = extrasEnvelope.width_n ?: 0

    override val widths
        get() = with(extrasEnvelope) {
            listOfNotNull(width_n, width_z, width_c, width_b, width_h, width_k, width_o)
        }
    override val heights
        get() = with(extrasEnvelope) {
            listOfNotNull(height_n, height_z, height_c, height_b, height_h, height_k, height_o)
        }
    override val urls
        get() = with(extrasEnvelope) {
            listOfNotNull(url_n, url_z, url_c, url_b, url_h, url_k, url_o)
        }

    fun backgroundImageUrl(displaySize: Point) =
        getUrlOfSmallestPhotoToFillSize(displaySize.x, displaySize.y, widths, heights, urls)

}
