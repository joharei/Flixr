package no.joharei.flixr.mainpage.models

import com.google.gson.annotations.SerializedName
import no.joharei.flixr.utils.Constants
import java.util.*

data class Contact(
        val nsid: String,
        @SerializedName("username")
        val userName: String,
        @SerializedName("iconserver")
        val iconServer: Int,
        @SerializedName("iconfarm")
        val iconFarm: Int,
        val ignored: Int,
        val revIgnored: Int,
        @SerializedName("realname")
        val realName: String,
        val friend: Int,
        val family: Int,
        val pathAlias: String?,
        val location: String
) {
    val cardImageUrl: String
        get() {
            if (iconServer > 0) {
                return String.format(Locale.getDefault(),
                        Constants.BUDDY_ICON_URL_FORMAT,
                        iconFarm,
                        iconServer,
                        nsid
                )
            } else {
                return Constants.MISSING_BUDDY_ICON_URL
            }
        }
}
