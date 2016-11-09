package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName

data class Description(
        @SerializedName("_content")
        val content: String
)
