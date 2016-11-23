package model;

/**
 * Created by Egor on 07.11.2016.
 */
public class Account implements Identifiable {

    private int id;
    private int userId;
    private int authDataId;

    public Account() {
    }

    public Account(int id, int userId, int authDataId) {

        this.id = id;
        this.userId = userId;
        this.authDataId = authDataId;
    }

    public Account(int userId, int authDataId) {
        this(-1, userId, authDataId);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAuthDataId() {
        return authDataId;
    }

    public void setAuthDataId(int authDataId) {
        this.authDataId = authDataId;
    }
}
