
package no.joharei.flixr.network.models;

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

    /**
     * @return The nsid
     */
    public String getNsid() {
        return nsid;
    }

    /**
     * @param nsid The nsid
     */
    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The iconserver
     */
    public int getIconserver() {
        return iconserver;
    }

    /**
     * @param iconserver The iconserver
     */
    public void setIconserver(int iconserver) {
        this.iconserver = iconserver;
    }

    /**
     * @return The iconfarm
     */
    public int getIconfarm() {
        return iconfarm;
    }

    /**
     * @param iconfarm The iconfarm
     */
    public void setIconfarm(int iconfarm) {
        this.iconfarm = iconfarm;
    }

    /**
     * @return The ignored
     */
    public int getIgnored() {
        return ignored;
    }

    /**
     * @param ignored The ignored
     */
    public void setIgnored(int ignored) {
        this.ignored = ignored;
    }

    /**
     * @return The revIgnored
     */
    public int getRevIgnored() {
        return revIgnored;
    }

    /**
     * @param revIgnored The rev_ignored
     */
    public void setRevIgnored(int revIgnored) {
        this.revIgnored = revIgnored;
    }

    /**
     * @return The realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname The realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return The friend
     */
    public int getFriend() {
        return friend;
    }

    /**
     * @param friend The friend
     */
    public void setFriend(int friend) {
        this.friend = friend;
    }

    /**
     * @return The family
     */
    public int getFamily() {
        return family;
    }

    /**
     * @param family The family
     */
    public void setFamily(int family) {
        this.family = family;
    }

    /**
     * @return The pathAlias
     */
    public Object getPathAlias() {
        return pathAlias;
    }

    /**
     * @param pathAlias The path_alias
     */
    public void setPathAlias(Object pathAlias) {
        this.pathAlias = pathAlias;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
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
