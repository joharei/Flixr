package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
class Photos {

    private String id;
    private String primary;
    private String owner;
    @SerializedName("ownername")
    private String ownerName;
    @SerializedName("photo")
    private List<Photo> photos = new ArrayList<>();
    private int page;
    @SerializedName("perpage")
    private int perPage;
    private int pages;
    private String total;
    private String title;

    List<Photo> getPhotos() {
        return photos;
    }
}
