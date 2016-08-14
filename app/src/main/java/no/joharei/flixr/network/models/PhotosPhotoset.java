package no.joharei.flixr.network.models;

import java.util.ArrayList;
import java.util.List;

public class PhotosPhotoset {

    private String id;
    private String primary;
    private String owner;
    private String ownername;
    private List<Photo> photo = new ArrayList<Photo>();
    private int page;
    private int perPage;
    private int perpage;
    private int pages;
    private String total;
    private String title;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The primary
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * @param primary The primary
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return The ownername
     */
    public String getOwnername() {
        return ownername;
    }

    /**
     * @param ownername The ownername
     */
    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    /**
     * @return The photo
     */
    public List<Photo> getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    /**
     * @return The page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return The perPage
     */
    public int getPerPage() {
        return perPage;
    }

    /**
     * @param perPage The per_page
     */
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    /**
     * @return The perpage
     */
    public int getPerpage() {
        return perpage;
    }

    /**
     * @param perpage The perpage
     */
    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    /**
     * @return The pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
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
}
