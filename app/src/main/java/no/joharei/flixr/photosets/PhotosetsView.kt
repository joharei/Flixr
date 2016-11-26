package no.joharei.flixr.photosets

import android.content.Context

import no.joharei.flixr.api.models.Photoset

internal interface PhotosetsView {
    fun getContext(): Context

    fun showProgress()

    fun hideProgress()

    fun showPhotosets(photosets: List<Photoset>)
}
