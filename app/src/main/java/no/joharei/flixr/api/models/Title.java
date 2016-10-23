
package no.joharei.flixr.api.models;

import com.google.gson.annotations.SerializedName;

public class Title {

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

}
