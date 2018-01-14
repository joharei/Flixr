package no.joharei.flixr.common.adapters

interface PhotoItem {
    val thumbnailWidth: Int
    val thumbnailHeight: Int

    fun photoUrl(width: Int, height: Int): String
}