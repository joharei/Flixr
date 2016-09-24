package no.joharei.flixr.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("isprimary")
    private String isPrimary;
    @SerializedName("ispublic")
    private int isPublic;
    @SerializedName("isfriend")
    private int isFriend;
    @SerializedName("isfamily")
    private int isFamily;

    private Photo(Parcel in) {
        id = in.readLong();
        secret = in.readString();
        server = in.readInt();
        farm = in.readInt();
        title = in.readString();
        isPrimary = in.readString();
        isPublic = in.readInt();
        isFriend = in.readInt();
        isFamily = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(secret);
        parcel.writeInt(server);
        parcel.writeInt(farm);
        parcel.writeString(title);
        parcel.writeString(isPrimary);
        parcel.writeInt(isPublic);
        parcel.writeInt(isFriend);
        parcel.writeInt(isFamily);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
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
