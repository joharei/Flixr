package no.joharei.flixr.common.adapters

interface PhotoItem {
    val thumbnailWidth: Int
    val thumbnailHeight: Int

    fun thumbnailUrl(fillWidth: Int, fillHeight: Int): String
}