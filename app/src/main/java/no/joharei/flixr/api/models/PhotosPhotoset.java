package no.joharei.flixr.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotosPhotoset {

    private String id;
    private String primary;
    private String owner;
    private String ownername;
    @SerializedName("photo")
    private List<Photo> photos = new ArrayList<>();
    private int page;
    private int perPage;
    private int perpage;
    private int pages;
    private String total;
    private String title;

    public String getId() {
        return id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getTitle() {
        return title;
    }
}
