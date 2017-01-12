package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName

data class PrimaryPhotoExtras(
        @SerializedName("url_n")
        val thumbnailUrl: String,
        @SerializedName("url_k")
        val backgroundImageUrl: String
)