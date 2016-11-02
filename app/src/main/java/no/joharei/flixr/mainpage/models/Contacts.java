package no.joharei.flixr.mainpage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Contacts {

    private int page;
    private int pages;
    private int perPage;
    private int perpage;
    private int total;
    @SerializedName("contact")
    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }
}
