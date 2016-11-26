package no.joharei.flixr.api

import android.content.Context
import android.content.SharedPreferences
import no.joharei.flixr.api.models.AuthToken
import oauth.signpost.OAuth.OAUTH_TOKEN
import oauth.signpost.OAuth.OAUTH_TOKEN_SECRET
import javax.inject.Inject


class LocalCredentialStore @Inject constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("token_preferences", Context.MODE_PRIVATE)

    val token: AuthToken
        get() = AuthToken(
                prefs.getString(OAUTH_TOKEN, ""),
                prefs.getString(OAUTH_TOKEN_SECRET, "")
        )

    fun store(authToken: AuthToken) {
        val editor = prefs.edit()
        editor.putString(OAUTH_TOKEN, authToken.authToken)
        editor.putString(OAUTH_TOKEN_SECRET, authToken.authTokenSecret)
        editor.apply()
    }

    fun clear() {
        val editor = prefs.edit()
        editor.remove(OAUTH_TOKEN)
        editor.remove(OAUTH_TOKEN_SECRET)
        editor.apply()
    }

    fun noToken(): Boolean {
        val token = token
        return token.authToken.isEmpty() || token.authTokenSecret.isEmpty()
    }
}
