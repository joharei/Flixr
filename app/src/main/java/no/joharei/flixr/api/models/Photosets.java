
package no.joharei.flixr.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photosets {

    private int cancreate;
    private int page;
    private int pages;
    private int perpage;
    private int total;
    @SerializedName("photoset")
    private List<Photoset> photosets = new ArrayList<>();

    public List<Photoset> getPhotosets() {
        return photosets;
    }
}
