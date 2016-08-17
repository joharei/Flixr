package no.joharei.flixr.utils;


public class Constants {
    // Your Base URL for the site
    public static final String BASE_AUTH_URL = "https://www.flickr.com/services/";
    public static final String BASE_URL = "https://api.flickr.com/services/rest/";

    public static final String REQUEST_URL = BASE_AUTH_URL + "oauth/request_token";
    public static final String ACCESS_URL = BASE_AUTH_URL + "oauth/access_token";
    public static final String AUTHORIZE_URL = BASE_AUTH_URL + "oauth/authorize";

    public static final String ENCODING = "UTF-8";

    public static final String OAUTH_CALLBACK_URL = "http://localhost/";

    public static final String THUMBNAIL_URL_FORMAT = "https://farm%d.static.flickr.com/%d/%d_%s_n.jpg";
    public static final String FULLSCREEN_URL_FORMAT = "https://farm%d.static.flickr.com/%d/%d_%s_h.jpg";
    public static final String BUDDY_ICON_URL_FORMAT = "http://farm%d.staticflickr.com/%d/buddyicons/%s.jpg";
    public static final String MISSING_BUDDY_ICON_URL = "https://www.flickr.com/images/buddyicon.gif";

    public static final int BACKGROUND_UPDATE_DELAY = 300;
}
