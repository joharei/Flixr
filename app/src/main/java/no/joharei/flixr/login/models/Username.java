package no.joharei.flixr.login.models;

import com.google.gson.annotations.SerializedName;

public class Username {

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
