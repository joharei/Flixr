
package no.joharei.flixr.mainpage.models;

import java.util.Locale;

import no.joharei.flixr.utils.Constants;

public class Contact {

    private String nsid;
    private String username;
    private int iconserver;
    private int iconfarm;
    private int ignored;
    private int revIgnored;
    private String realname;
    private int friend;
    private int family;
    private Object pathAlias;
    private String location;

    public String getNsid() {
        return nsid;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public String getCardImageUrl() {
        if (iconserver > 0) {
            return String.format(Locale.getDefault(),
                    Constants.BUDDY_ICON_URL_FORMAT,
                    iconfarm,
                    iconserver,
                    nsid
            );
        } else {
            return Constants.MISSING_BUDDY_ICON_URL;
        }
    }
}
