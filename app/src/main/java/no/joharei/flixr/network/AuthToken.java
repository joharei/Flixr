package no.joharei.flixr.network;


public class AuthToken {
    private String authToken;
    private String authTokenSecret;
    private String username;
    private String userNsid;

    public AuthToken(String authToken, String authTokenSecret, String username, String userNsid) {
        this.authToken = authToken;
        this.authTokenSecret = authTokenSecret;
        this.username = username;
        this.userNsid = userNsid;
    }

    public String getAuthToken() {

        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthTokenSecret() {
        return authTokenSecret;
    }

    public void setAuthTokenSecret(String authTokenSecret) {
        this.authTokenSecret = authTokenSecret;
    }

    public String getUsername() {
        return username;
    }

    public String getUserNsid() {
        return userNsid;
    }
}
