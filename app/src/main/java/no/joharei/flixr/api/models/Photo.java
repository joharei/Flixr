package no.joharei.flixr.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

import no.joharei.flixr.utils.Constants;

public class Photo implements Parcelable {

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
    private long id;
    private String secret;
    private int server;
    private int farm;
    private String title;
    private String isprimary;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    private Photo(Parcel in) {
        id = in.readLong();
        secret = in.readString();
        server = in.readInt();
        farm = in.readInt();
        title = in.readString();
        isprimary = in.readString();
        ispublic = in.readInt();
        isfriend = in.readInt();
        isfamily = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(secret);
        parcel.writeInt(server);
        parcel.writeInt(farm);
        parcel.writeString(title);
        parcel.writeString(isprimary);
        parcel.writeInt(ispublic);
        parcel.writeInt(isfriend);
        parcel.writeInt(isfamily);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getFullscreenImageUrl() {
        return String.format(Locale.getDefault(),
                Constants.FULLSCREEN_URL_FORMAT,
                farm,
                server,
                id,
                secret
        );
    }
}
