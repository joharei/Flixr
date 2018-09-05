package no.joharei.flixr.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Title(
    @Json(name = "_content")
    val content: String
)
