package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Photosets {

    @SerializedName("cancreate")
    private int canCreate;
    private int page;
    private int pages;
    @SerializedName("perpage")
    private int perPage;
    private int total;
    @SerializedName("photoset")
    private List<Photoset> photosets = new ArrayList<>();

    public List<Photoset> getPhotosets() {
        return photosets;
    }

}
