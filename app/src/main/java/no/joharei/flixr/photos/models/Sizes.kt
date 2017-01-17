package no.joharei.flixr.photos.models

import com.google.gson.annotations.SerializedName

data class Sizes(
        @SerializedName("size")
        val sizes: List<Size>
)