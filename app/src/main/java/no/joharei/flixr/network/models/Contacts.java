package no.joharei.flixr.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
class Contacts {

    private int page;
    private int pages;
    @SerializedName("perpage")
    private int perPage;
    private int total;
    @SerializedName("contact")
    private List<Contact> contacts = new ArrayList<>();

    List<Contact> getContacts() {
        return contacts;
    }
}
