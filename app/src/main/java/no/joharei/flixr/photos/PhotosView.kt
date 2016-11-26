package no.joharei.flixr.photos

import android.content.Context

import no.joharei.flixr.api.models.Photo

internal interface PhotosView {
    fun getContext(): Context

    fun showPhotos(photos: List<Photo>)
}
