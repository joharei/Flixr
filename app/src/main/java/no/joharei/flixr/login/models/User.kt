package no.joharei.flixr.login.models

import com.google.gson.annotations.SerializedName

data class User(val id: String, @SerializedName("username") val usernameEnvelope: Username) {
    val username: String get() = usernameEnvelope.content
}
