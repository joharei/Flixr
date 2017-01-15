package no.joharei.flixr.login

import android.content.Context

interface LoginView {
    val context: Context

    fun showProgress(show: Boolean)

    fun loadUrl(url: String)

    fun getUserDetailsCompleted()
}
