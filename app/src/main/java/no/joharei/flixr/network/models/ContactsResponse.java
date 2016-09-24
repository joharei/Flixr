
package no.joharei.flixr.network.models;

import java.util.List;

@SuppressWarnings("unused")
public class ContactsResponse {

    private Contacts contacts;
    private String stat;

    public List<Contact> getContacts() {
        return contacts.getContacts();
    }

}
