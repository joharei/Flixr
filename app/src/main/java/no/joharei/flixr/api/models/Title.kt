package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName

data class Title(
        @SerializedName("_content")
        val content: String
)
