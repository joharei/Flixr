package no.joharei.flixr.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.f2prateek.dart.HensonNavigable;

import no.joharei.flixr.R;
import no.joharei.flixr.api.AuthToken;
import no.joharei.flixr.api.LocalCredentialStore;
import no.joharei.flixr.mainpage.MainActivity;
import no.joharei.flixr.utils.ApiKeys;
import no.joharei.flixr.utils.Constants;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthProvider;


@HensonNavigable
public class LoginActivity extends Activity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private OkHttpOAuthConsumer consumer;
    private OkHttpOAuthProvider provider;
    private WebView webView;
    private ProgressBar progress;
    private LoginPresenter loginPresenter;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);

        setContentView(R.layout.activity_login);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        progress.setIndeterminate(true);

        try {
            consumer = new OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET);
            provider = new OkHttpOAuthProvider(Constants.REQUEST_URL, Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
        } catch (Exception e) {
            Log.e(TAG, "Error creating consumer/provider", e);
        }
        Log.d(TAG, "Starting task to retrieve request token");
        new OAuthRequestTokenTask(consumer, provider).execute();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {

                if (url.startsWith(Constants.OAUTH_CALLBACK_URL)) {
                    if (url.contains("oauth_token=")) {
                        webView.setVisibility(View.INVISIBLE);
                        new RetrieveAccessTokenTask(consumer, provider).execute(Uri.parse(url));
                    } else {
                        webView.setVisibility(View.VISIBLE);
                    }
                }
                progress.setVisibility(View.GONE);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.stop();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress(boolean show) {
        // TODO: implement progress bar
    }

    @Override
    public void getUserDetailsCompleted() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * An asynchronous task that communicates with Twitter to
     * retrieve a request token.
     * (OAuthGetRequestToken)
     * <p>
     * After receiving the request token from Twitter,
     * pop a browser to the user to authorize the Request Token.
     * (OAuthAuthorizeToken)
     */
    private class OAuthRequestTokenTask extends AsyncTask<Void, Void, String> {

        final String TAG = getClass().getName();
        private OAuthProvider provider;
        private OAuthConsumer consumer;

        /**
         * We pass the OAuth consumer and provider.
         *
         * @param provider The OAuthProvider object
         * @param consumer The OAuthConsumer object
         */
        private OAuthRequestTokenTask(OAuthConsumer consumer, OAuthProvider provider) {
            this.consumer = consumer;
            this.provider = provider;
        }

        /**
         * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
         */
        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.i(TAG, "Retrieving request token from Google servers");
                return provider.retrieveRequestToken(consumer, Constants.OAUTH_CALLBACK_URL);
            } catch (Exception e) {
                Log.e(TAG, "Error during OAUth retrieve request token", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String url) {
            Log.i(TAG, "Popping a browser with the authorize URL : " + url);
            webView.loadUrl(url);
        }
    }

    private class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

        private OAuthProvider provider;
        private OAuthConsumer consumer;

        private RetrieveAccessTokenTask(OAuthConsumer consumer, OAuthProvider provider) {
            this.consumer = consumer;
            this.provider = provider;
        }

        /**
         * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
         * for future API calls.
         */
        @Override
        protected Void doInBackground(Uri... params) {
            final Uri uri = params[0];
            final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

            try {
                provider.retrieveAccessToken(consumer, oauth_verifier);

                LocalCredentialStore credentialStore = new LocalCredentialStore(LoginActivity.this);
                AuthToken authToken = new AuthToken(consumer.getToken(), consumer.getTokenSecret());
                credentialStore.store(authToken);

                String token = authToken.getAuthToken();
                String secret = authToken.getAuthTokenSecret();

                consumer.setTokenWithSecret(token, secret);

                loginPresenter.getUserDetails();

                Log.i(TAG, "OAuth - Access Token Retrieved");

            } catch (Exception e) {
                Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
            }

            return null;
        }
    }
}