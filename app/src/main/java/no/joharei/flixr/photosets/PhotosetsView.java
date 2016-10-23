package no.joharei.flixr.photosets;

import android.content.Context;

import java.util.List;

import no.joharei.flixr.api.models.Photoset;

interface PhotosetsView {
    Context getContext();

    void showPhotosets(List<Photoset> photosets);
}
