package no.joharei.flixr.mainpage;

import android.content.Context;

import java.util.List;

import no.joharei.flixr.api.models.Photoset;
import no.joharei.flixr.mainpage.models.Contact;

interface MainView {
    Context getContext();

    void showMyPhotosets(List<Photoset> photosets);

    void showMyContacts(List<Contact> contacts);
}
