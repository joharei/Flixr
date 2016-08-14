package no.joharei.flixr.preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class CommonPreferences {
    private static final String COMMON_PREFS = "common_preferences";
    private static final String USER_NSID = "user_nsid";
    private static final String USERNAME = "username";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(COMMON_PREFS, Context.MODE_PRIVATE);
    }

    private static void setPreference(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    private static String getPreference(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static void setUserNsid(Context context, String userNsid) {
        setPreference(context, USER_NSID, userNsid);
    }

    public static String getUserNsid(Context context) {
        return getPreference(context, USER_NSID);
    }

    public static void setUsername(Context context, String username) {
        setPreference(context, USERNAME, username);
    }

    public static String getUsername(Context context) {
        return getPreference(context, USERNAME);
    }
}
