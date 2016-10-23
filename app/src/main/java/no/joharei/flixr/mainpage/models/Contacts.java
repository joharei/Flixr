
package no.joharei.flixr.mainpage.models;

import java.util.ArrayList;
import java.util.List;

public class Contacts {

    private int page;
    private int pages;
    private int perPage;
    private int perpage;
    private int total;
    private List<Contact> contact = new ArrayList<>();

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
     * @return The total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return The contact
     */
    public List<Contact> getContact() {
        return contact;
    }

    /**
     * @param contact The contact
     */
    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

}
