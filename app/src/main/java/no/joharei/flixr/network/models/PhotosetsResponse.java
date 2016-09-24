package no.joharei.flixr.network.models;

import java.util.List;

@SuppressWarnings("unused")
public class PhotosetsResponse {

    private Photosets photosets;
    private String stat;

    public List<Photoset> getPhotosets() {
        return photosets.getPhotosets();
    }

}
