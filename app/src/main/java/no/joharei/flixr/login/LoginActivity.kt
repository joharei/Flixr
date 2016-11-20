package no.joharei.flixr.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

import com.f2prateek.dart.HensonNavigable

import no.joharei.flixr.R
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.api.models.AuthToken
import no.joharei.flixr.mainpage.MainActivity
import no.joharei.flixr.utils.ApiKeys
import no.joharei.flixr.utils.Constants
import oauth.signpost.OAuth
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
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
        OAuthRequestTokenTask(consumer, provider).execute()
        webView.setWebViewClient(object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                if (url!!.startsWith(Constants.OAUTH_CALLBACK_URL)) {
                    if (url.contains("oauth_token=")) {
                        webView.visibility = View.INVISIBLE
                        RetrieveAccessTokenTask(consumer, provider).execute(Uri.parse(url))
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

    override fun getUserDetailsCompleted() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * An asynchronous task that communicates with Twitter to
     * retrieve a request token.
     * (OAuthGetRequestToken)
     *
     *
     * After receiving the request token from Twitter,
     * pop a browser to the user to authorize the Request Token.
     * (OAuthAuthorizeToken)
     */
    inner class OAuthRequestTokenTask
    /**
     * We pass the OAuth consumer and provider.

     * @param provider The OAuthProvider object
     * *
     * @param consumer The OAuthConsumer object
     */
    constructor(private val consumer: OAuthConsumer, private val provider: OAuthProvider) : AsyncTask<Void, Void, String>() {

        internal val TAG = javaClass.name

        /**
         * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
         */
        override fun doInBackground(vararg params: Void): String? {

            try {
                Log.i(TAG, "Retrieving request token from Google servers")
                return provider.retrieveRequestToken(consumer, Constants.OAUTH_CALLBACK_URL)
            } catch (e: Exception) {
                Log.e(TAG, "Error during OAUth retrieve request token", e)
            }

            return null
        }

        override fun onPostExecute(url: String) {
            Log.i(TAG, "Popping a browser with the authorize URL : " + url)
            webView.loadUrl(url)
        }
    }

    inner class RetrieveAccessTokenTask constructor(private val consumer: OAuthConsumer, private val provider: OAuthProvider) : AsyncTask<Uri, Void, Void>() {

        /**
         * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
         * for future API calls.
         */
        override fun doInBackground(vararg params: Uri): Void? {
            val uri = params[0]
            val oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER)

            try {
                provider.retrieveAccessToken(consumer, oauth_verifier)

                val credentialStore = LocalCredentialStore(this@LoginActivity)
                val authToken = AuthToken(consumer.token, consumer.tokenSecret)
                credentialStore.store(authToken)

                val token = authToken.authToken
                val secret = authToken.authTokenSecret

                consumer.setTokenWithSecret(token, secret)

                loginPresenter.getUserDetails()

                info("OAuth - Access Token Retrieved")

            } catch (e: Exception) {
                error("OAuth - Access Token Retrieval Error", e)
            }

            return null
        }
    }
}
