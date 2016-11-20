package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import no.joharei.flixr.utils.Constants
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable
import java.util.*

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
        val isFamily: Int
) : PaperParcelable {
    val cardImageUrl: String
        get() = String.format(Locale.getDefault(),
                Constants.THUMBNAIL_URL_FORMAT,
                farm,
                server,
                id,
                secret
        )

    val fullscreenImageUrl: String
        get() = String.format(Locale.getDefault(),
                Constants.FULLSCREEN_URL_FORMAT,
                farm,
                server,
                id,
                secret
        )

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Photo::class.java)
    }
}
