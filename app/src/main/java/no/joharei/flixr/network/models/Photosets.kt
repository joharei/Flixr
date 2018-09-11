package no.joharei.flixr.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosetsResponse(val photosets: Photosets)

@JsonClass(generateAdapter = true)
data class Photosets(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    @Json(name = "photoset")
    val photosets: List<Photoset>
)