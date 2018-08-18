package no.joharei.flixr.api.models

import no.joharei.flixr.common.getUrlOfSmallestPhotoToFillSize

data class PrimaryPhotoExtras(
        val widthN: Int?,
        val widthZ: Int?,
        val widthC: Int?,
        val widthB: Int?,
        val widthH: Int?,
        val widthK: Int?,
        val widthO: Int?,
        val heightN: Int?,
        val heightZ: Int?,
        val heightC: Int?,
        val heightB: Int?,
        val heightH: Int?,
        val heightK: Int?,
        val heightO: Int?,
        val urlN: String?,
        val urlZ: String?,
        val urlC: String?,
        val urlB: String?,
        val urlH: String?,
        val urlK: String?,
        val urlO: String?
) {
    fun backgroundImageUrl(fillWidth: Int, fillHeight: Int) = getUrlOfSmallestPhotoToFillSize(
            fillWidth,
            fillHeight,
            arrayOf(widthN, widthZ, widthC, widthB, widthH, widthK, widthO).filterNotNull(),
            arrayOf(heightN, heightZ, heightC, heightB, heightH, heightK, heightO).filterNotNull(),
            arrayOf(urlN, urlZ, urlC, urlB, urlH, urlK, urlO).filterNotNull()
    )
}