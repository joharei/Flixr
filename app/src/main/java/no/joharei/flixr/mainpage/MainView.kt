package no.joharei.flixr.mainpage

import android.content.Context

import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.mainpage.models.Contact

interface MainView {
    fun getViewContext(): Context

    fun showMyPhotosets(photosets: List<Photoset>)

    fun showMyContacts(contacts: List<Contact>)
}
