package no.joharei.flixr.api;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import static oauth.signpost.OAuth.OAUTH_TOKEN;
import static oauth.signpost.OAuth.OAUTH_TOKEN_SECRET;


public class LocalCredentialStore {

    private static final String TOKEN_PREFS = "token_preferences";

    private SharedPreferences prefs;

    @Inject
    public LocalCredentialStore(Context context) {
        prefs = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE);
    }

    public AuthToken getToken() {
        return new AuthToken(
                prefs.getString(OAUTH_TOKEN, ""),
                prefs.getString(OAUTH_TOKEN_SECRET, "")
        );
    }

    public void store(AuthToken authToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(OAUTH_TOKEN, authToken.getAuthToken());
        editor.putString(OAUTH_TOKEN_SECRET, authToken.getAuthTokenSecret());
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(OAUTH_TOKEN);
        editor.remove(OAUTH_TOKEN_SECRET);
        editor.apply();
    }

    public boolean noToken() {
        AuthToken token = getToken();
        return token.getAuthToken().isEmpty() || token.getAuthTokenSecret().isEmpty();
    }
}
