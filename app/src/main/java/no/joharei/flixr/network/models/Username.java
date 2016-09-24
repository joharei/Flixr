package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
class Username {

    @SerializedName("_content")
    private String content;

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
