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
import android.widget.ProgressBar
import com.f2prateek.dart.HensonNavigable
import no.joharei.flixr.R
import no.joharei.flixr.mainpage.MainActivity
import no.joharei.flixr.utils.ApiKeys
import no.joharei.flixr.utils.Constants
import org.jetbrains.anko.*
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.OkHttpOAuthProvider


@HensonNavigable
class LoginActivity : Activity(), LoginView, AnkoLogger {
    lateinit var consumer: OkHttpOAuthConsumer
    lateinit var provider: OkHttpOAuthProvider
    lateinit var webView: WebView
    lateinit var progress: ProgressBar
    val loginPresenter: LoginPresenter = LoginPresenter()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ID_PROGRESS = 1
        relativeLayout {
            progress = horizontalProgressBar {
                id = ID_PROGRESS
                horizontalPadding = R.dimen.activity_horizontal_margin
                verticalPadding = R.dimen.activity_vertical_margin
                visibility = View.GONE
                isIndeterminate = true
            }.lparams(height = dip(20)) {
                centerHorizontally()
            }
            webView = webView().lparams {
                below(ID_PROGRESS)
            }.lparams(width = matchParent, height = matchParent)
        }

        loginPresenter.attachView(this)

        webView.settings.javaScriptEnabled = true

        try {
            consumer = OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET)
            provider = OkHttpOAuthProvider(Constants.REQUEST_URL, Constants.ACCESS_URL, Constants.AUTHORIZE_URL)
        } catch (e: Exception) {
            error("Error creating consumer/provider", e)
        }

        debug("Starting task to retrieve request token")
        loginPresenter.retrieveRequestToken(consumer, provider)
        webView.setWebViewClient(object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                if (url!!.startsWith(Constants.OAUTH_CALLBACK_URL)) {
                    if (url.contains("oauth_token=")) {
                        webView.visibility = View.INVISIBLE
                        loginPresenter.retrieveAccessToken(consumer, provider, Uri.parse(url))
                    } else {
                        webView.visibility = View.VISIBLE
                    }
                }
                progress.visibility = View.GONE
            }

        })
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
        webView.loadUrl(url)
    }

    override fun getUserDetailsCompleted() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
