package no.joharei.flixr.mainpage.models

import com.google.gson.annotations.SerializedName

data class Contacts(
        val page: Int,
        val pages: Int,
        val perPage: Int,
        val total: Int,
        @SerializedName("contact")
        val contacts: List<Contact>
)
