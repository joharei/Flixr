package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

public class Username {

    @SerializedName("_content")
    private String content;

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The _content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
