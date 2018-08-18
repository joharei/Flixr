package no.joharei.flixr.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.f2prateek.dart.HensonNavigable
import kotlinx.android.synthetic.main.activity_login.*
import no.joharei.flixr.R
import no.joharei.flixr.common.ApiKeys
import no.joharei.flixr.common.Constants
import no.joharei.flixr.mainpage.MainActivity
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.OkHttpOAuthProvider
import timber.log.Timber


@HensonNavigable
class LoginActivity : Activity(), LoginView {
    lateinit var consumer: OkHttpOAuthConsumer
    lateinit var provider: OkHttpOAuthProvider
    val loginPresenter: LoginPresenter = LoginPresenter()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginPresenter.attachView(this)

        web_view.settings.javaScriptEnabled = true

        try {
            consumer = OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET)
            provider = OkHttpOAuthProvider(Constants.REQUEST_URL, Constants.ACCESS_URL, Constants.AUTHORIZE_URL)
        } catch (e: Exception) {
            Timber.e(e, "Error creating consumer/provider")
        }

        Timber.d("Starting task to retrieve request token")
        loginPresenter.retrieveRequestToken(consumer, provider)
        web_view.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                if (url?.startsWith(Constants.OAUTH_CALLBACK_URL) == true) {
                    if (url.contains("oauth_token=")) {
                        web_view.visibility = View.INVISIBLE
                        loginPresenter.retrieveAccessToken(consumer, provider, Uri.parse(url))
                    } else {
                        web_view.visibility = View.VISIBLE
                    }
                }
                progress.visibility = View.GONE
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.stop()
    }

    override val context: Context
        get() = this

    override fun showProgress(show: Boolean) {
        progress.visibility = View.VISIBLE
    }

    override fun loadUrl(url: String) {
        web_view.loadUrl(url)
    }

    override fun getUserDetailsCompleted() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
