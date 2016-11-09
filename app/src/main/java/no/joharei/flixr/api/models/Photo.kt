package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import no.joharei.flixr.utils.Constants
import org.parceler.Parcel
import java.util.*

@Parcel
data class Photo(
        val id: Long = 0,
        val secret: String? = null,
        val server: Int = 0,
        val farm: Int = 0,
        val title: String? = null,
        @SerializedName("isprimary")
        val isPrimary: String? = null,
        @SerializedName("ispublic")
        val isPublic: Int = 0,
        @SerializedName("isfriend")
        val isFriend: Int = 0,
        @SerializedName("isfamily")
        val isFamily: Int = 0
) {
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
}
