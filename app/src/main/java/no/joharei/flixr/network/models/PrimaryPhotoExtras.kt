package no.joharei.flixr.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrimaryPhotoExtras(
    val width_n: Int?,
    val width_z: Int?,
    val width_c: Int?,
    val width_b: Int?,
    val width_h: Int?,
    val width_k: Int?,
    val width_o: Int?,
    val height_n: Int?,
    val height_z: Int?,
    val height_c: Int?,
    val height_b: Int?,
    val height_h: Int?,
    val height_k: Int?,
    val height_o: Int?,
    val url_n: String?,
    val url_z: String?,
    val url_c: String?,
    val url_b: String?,
    val url_h: String?,
    val url_k: String?,
    val url_o: String?
)