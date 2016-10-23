
package no.joharei.flixr.api.models;

public class PhotosetsContainer {

    private Photosets photosets;
    private String stat;

    /**
     * @return The photosets
     */
    public Photosets getPhotosets() {
        return photosets;
    }

    /**
     * @param photosets The photosets
     */
    public void setPhotosets(Photosets photosets) {
        this.photosets = photosets;
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
