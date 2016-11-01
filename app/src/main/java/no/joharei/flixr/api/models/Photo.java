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

    public long getId() {
        return id;
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
