package no.joharei.flixr.mainpage.models

import com.squareup.moshi.JsonClass
import no.joharei.flixr.common.Constants
import java.util.*

@JsonClass(generateAdapter = true)
data class Contact(
    val nsid: String,
    val username: String,
    val iconserver: Int,
    val iconfarm: Int,
    val ignored: Int,
    val rev_ignored: Int,
    val realname: String,
    val friend: Int,
    val family: Int,
    val path_alias: String?,
    val location: String?
) {
    val cardImageUrl: String
        get() {
            if (iconserver > 0) {
                return String.format(
                    Locale.getDefault(),
                    Constants.BUDDY_ICON_URL_FORMAT,
                    iconfarm,
                    iconserver,
                    nsid
                )
            } else {
                return Constants.MISSING_BUDDY_ICON_URL
            }
        }

    val displayName get() = if (!realname.isEmpty()) realname else username

    val thumbnailDim get() = 300
}
