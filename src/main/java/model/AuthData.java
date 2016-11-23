package model;

/**
 * Created by Egor on 07.11.2016.
 */
public class AuthData implements Identifiable {

    private int id;
    private String username;
    private String password;

    public AuthData() {
    }

    public AuthData(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public AuthData(String username, String password) {
        this(-1, username, password);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + 31 * password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (getClass() != obj.getClass()) return false;
        AuthData authData = (AuthData) obj;
        return hashCode() == authData.hashCode()
                && username.equals(authData.username)
                && password.equals(authData.password);
    }

    @Override
    public String toString() {
        return id + " | " + username + " | " + password;
    }
}
