package no.joharei.flixr.mainpage

import android.content.Context
import no.joharei.flixr.mainpage.models.Contact
import no.joharei.flixr.network.models.Photoset

interface MainView {
    fun getViewContext(): Context

    fun showMyPhotosets(photosets: List<Photoset>)

    fun showMyContacts(contacts: List<Contact>)
}
