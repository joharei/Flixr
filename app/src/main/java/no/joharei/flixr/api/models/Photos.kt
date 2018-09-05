package no.joharei.flixr.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosResponse(val photoset: Photos)

@JsonClass(generateAdapter = true)
data class Photos(
    val id: String,
    val primary: String,
    val owner: String,
    val ownername: String,
    @Json(name = "photo")
    val photos: List<Photo>,
    val page: Int,
    val per_page: Int,
    val pages: Int,
    val total: String,
    val title: String
)