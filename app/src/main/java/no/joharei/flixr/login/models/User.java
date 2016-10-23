package no.joharei.flixr.login.models;

public class User {

    private String id;
    private Username username;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The username
     */
    public Username getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(Username username) {
        this.username = username;
    }
}
