package no.joharei.flixr.photos;

import android.content.Context;

import java.util.List;

import no.joharei.flixr.api.models.Photo;

interface PhotosView {
    Context getContext();

    void showPhotos(List<Photo> photos);
}
