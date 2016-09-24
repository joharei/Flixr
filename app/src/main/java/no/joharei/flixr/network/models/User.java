package no.joharei.flixr.network.models;

@SuppressWarnings("unused")
public class User {

    private String id;
    private Username username;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username.getContent();
    }
}
