
package no.joharei.flixr.network.models;

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

    /**
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return The primary
     */
    public long getPrimary() {
        return primary;
    }

    /**
     * @param primary The primary
     */
    public void setPrimary(long primary) {
        this.primary = primary;
    }

    /**
     * @return The secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret The secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return The server
     */
    public int getServer() {
        return server;
    }

    /**
     * @param server The server
     */
    public void setServer(int server) {
        this.server = server;
    }

    /**
     * @return The farm
     */
    public int getFarm() {
        return farm;
    }

    /**
     * @param farm The farm
     */
    public void setFarm(int farm) {
        this.farm = farm;
    }

    /**
     * @return The photos
     */
    public int getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(int photos) {
        this.photos = photos;
    }

    /**
     * @return The videos
     */
    public String getVideos() {
        return videos;
    }

    /**
     * @param videos The videos
     */
    public void setVideos(String videos) {
        this.videos = videos;
    }

    /**
     * @return The title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * @return The needsInterstitial
     */
    public int getNeedsInterstitial() {
        return needsInterstitial;
    }

    /**
     * @param needsInterstitial The needs_interstitial
     */
    public void setNeedsInterstitial(int needsInterstitial) {
        this.needsInterstitial = needsInterstitial;
    }

    /**
     * @return The visibilityCanSeeSet
     */
    public int getVisibilityCanSeeSet() {
        return visibilityCanSeeSet;
    }

    /**
     * @param visibilityCanSeeSet The visibility_can_see_set
     */
    public void setVisibilityCanSeeSet(int visibilityCanSeeSet) {
        this.visibilityCanSeeSet = visibilityCanSeeSet;
    }

    /**
     * @return The countViews
     */
    public String getCountViews() {
        return countViews;
    }

    /**
     * @param countViews The count_views
     */
    public void setCountViews(String countViews) {
        this.countViews = countViews;
    }

    /**
     * @return The countComments
     */
    public String getCountComments() {
        return countComments;
    }

    /**
     * @param countComments The count_comments
     */
    public void setCountComments(String countComments) {
        this.countComments = countComments;
    }

    /**
     * @return The canComment
     */
    public int getCanComment() {
        return canComment;
    }

    /**
     * @param canComment The can_comment
     */
    public void setCanComment(int canComment) {
        this.canComment = canComment;
    }

    /**
     * @return The dateCreate
     */
    public String getDateCreate() {
        return dateCreate;
    }

    /**
     * @param dateCreate The date_create
     */
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     * @return The dateUpdate
     */
    public String getDateUpdate() {
        return dateUpdate;
    }

    /**
     * @param dateUpdate The date_update
     */
    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
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
