
package no.joharei.flixr.api.models;

import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("_content")
    private String content;

    public String getContent() {
        return content;
    }
}
