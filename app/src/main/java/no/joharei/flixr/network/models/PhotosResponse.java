package no.joharei.flixr.network.models;

import java.util.List;

@SuppressWarnings("unused")
public class PhotosResponse {

    private Photos photoset;
    private String stat;

    public List<Photo> getPhotos() {
        return photoset.getPhotos();
    }

}
