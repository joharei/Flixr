package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
class Description {

    @SerializedName("_content")
    private String content;

    public String getContent() {
        return content;
    }
}
