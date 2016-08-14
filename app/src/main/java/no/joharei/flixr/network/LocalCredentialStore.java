package no.joharei.flixr.network;

import android.content.Context;
import android.content.SharedPreferences;


public class LocalCredentialStore {

    private static final String TOKEN_PREFS = "token_preferences";
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";

    private SharedPreferences prefs;

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
