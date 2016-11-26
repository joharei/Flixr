package no.joharei.flixr.preferences

import android.content.Context
import android.content.SharedPreferences


object CommonPreferences {
    private val COMMON_PREFS = "common_preferences"
    private val USER_NSID = "user_nsid"
    private val USERNAME = "username"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(COMMON_PREFS, Context.MODE_PRIVATE)
    }

    private fun setPreference(context: Context, key: String, value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }

    private fun getPreference(context: Context, key: String): String? {
        return getSharedPreferences(context).getString(key, null)
    }

    fun setUserNsid(context: Context, userNsid: String) {
        setPreference(context, USER_NSID, userNsid)
    }

    fun getUserNsid(context: Context): String? {
        return getPreference(context, USER_NSID)
    }

    fun setUsername(context: Context, username: String) {
        setPreference(context, USERNAME, username)
    }

    fun getUsername(context: Context): String? {
        return getPreference(context, USERNAME)
    }
}
