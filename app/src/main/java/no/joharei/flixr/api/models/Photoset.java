
package no.joharei.flixr.api.models;

import java.util.Locale;

import no.joharei.flixr.utils.Constants;

public class Photoset {

    private long id;
    private long primary;
    private String secret;
    private int server;
    private int farm;
    private int photos;
    private String videos;
    private Title title;
    private Description description;
    private int needsInterstitial;
    private int visibilityCanSeeSet;
    private String countViews;
    private String countComments;
    private int canComment;
    private String dateCreate;
    private String dateUpdate;

    public long getId() {
        return id;
    }

    public int getPhotos() {
        return photos;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public String getCardImageUrl() {
        return String.format(Locale.getDefault(),
                Constants.THUMBNAIL_URL_FORMAT,
                farm,
                server,
                primary,
                secret
        );
    }

    public String getBackgroundImageUrl() {
        return String.format(Locale.getDefault(),
                Constants.FULLSCREEN_URL_FORMAT,
                farm,
                server,
                primary,
                secret
        );
    }
}
