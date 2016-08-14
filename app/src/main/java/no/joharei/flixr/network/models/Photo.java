package no.joharei.flixr.network.models;

import java.util.Locale;

import no.joharei.flixr.utils.Constants;

public class Photo {

    private long id;
    private String secret;
    private int server;
    private int farm;
    private String title;
    private String isprimary;
    private int ispublic;
    private int isfriend;
    private int isfamily;

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
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The isprimary
     */
    public String getIsprimary() {
        return isprimary;
    }

    /**
     * @param isprimary The isprimary
     */
    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    /**
     * @return The ispublic
     */
    public int getIspublic() {
        return ispublic;
    }

    /**
     * @param ispublic The ispublic
     */
    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    /**
     * @return The isfriend
     */
    public int getIsfriend() {
        return isfriend;
    }

    /**
     * @param isfriend The isfriend
     */
    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    /**
     * @return The isfamily
     */
    public int getIsfamily() {
        return isfamily;
    }

    /**
     * @param isfamily The isfamily
     */
    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public String getCardImageUrl() {
        return String.format(Locale.getDefault(),
                Constants.THUMBNAIL_URL_FORMAT,
                farm,
                server,
                id,
                secret
        );
    }
}
