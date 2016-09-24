package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

import no.joharei.flixr.utils.Constants;

@SuppressWarnings("unused")
public class Contact {

    private String nsid;
    @SerializedName("username")
    private String userName;
    @SerializedName("iconserver")
    private int iconServer;
    @SerializedName("iconfarm")
    private int iconFarm;
    private int ignored;
    private int revIgnored;
    @SerializedName("realname")
    private String realName;
    private int friend;
    private int family;
    private Object pathAlias;
    private String location;

    public String getNsid() {
        return nsid;
    }

    public String getUserName() {
        return userName;
    }

    public String getRealName() {
        return realName;
    }

    public String getCardImageUrl() {
        if (iconServer > 0) {
            return String.format(Locale.getDefault(),
                    Constants.BUDDY_ICON_URL_FORMAT,
                    iconFarm,
                    iconServer,
                    nsid
            );
        } else {
            return Constants.MISSING_BUDDY_ICON_URL;
        }
    }
}
