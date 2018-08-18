package no.joharei.flixr.common


object Constants {
    // Your Base URL for the site
    internal val BASE_AUTH_URL = "https://www.flickr.com/services/"
    internal val BASE_URL = "https://api.flickr.com/services/rest/"

    internal val REQUEST_URL = BASE_AUTH_URL + "oauth/request_token"
    internal val ACCESS_URL = BASE_AUTH_URL + "oauth/access_token"
    internal val AUTHORIZE_URL = BASE_AUTH_URL + "oauth/authorize"

    internal val OAUTH_CALLBACK_URL = "x-oauth://callback/"

    internal val BUDDY_ICON_URL_FORMAT = "https://farm%d.staticflickr.com/%d/buddyicons/%s_r.jpg"
    internal val MISSING_BUDDY_ICON_URL = "https://www.flickr.com/images/buddyicon.gif"

    internal val BACKGROUND_UPDATE_DELAY = 300
}
