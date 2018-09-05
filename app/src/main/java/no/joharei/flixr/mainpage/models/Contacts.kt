package no.joharei.flixr.mainpage.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactsResponse(val contacts: Contacts)

@JsonClass(generateAdapter = true)
data class Contacts(
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val total: Int,
    @Json(name = "contact")
    val contacts: List<Contact>
)
