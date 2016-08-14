package no.joharei.flixr.network.models;

public class PhotosPhotosetContainer {

    private PhotosPhotoset photoset;
    private String stat;

    /**
     * @return The photoset
     */
    public PhotosPhotoset getPhotoset() {
        return photoset;
    }

    /**
     * @param photoset The photoset
     */
    public void setPhotoset(PhotosPhotoset photoset) {
        this.photoset = photoset;
    }

    /**
     * @return The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

}
