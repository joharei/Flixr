package no.joharei.flixr.login.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailsResponse(val user: User)

@JsonClass(generateAdapter = true)
data class User(val id: String, @Json(name = "username") val usernameEnvelope: Username) {
    val username: String get() = usernameEnvelope.content
}

@JsonClass(generateAdapter = true)
data class Username(@Json(name = "_content") val content: String)
