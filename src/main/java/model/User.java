package model;

/**
 * Created by Egor on 07.11.2016.
 */
public class User implements Identifiable {

    private int id;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName) {
        this(-1, firstName, lastName);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + 31 * lastName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return hashCode() == user.hashCode()
                && firstName.equals(user.firstName)
                && lastName.equals(user.lastName);
    }

    @Override
    public String toString() {
        return id + " | " + firstName + " " + lastName;
    }
}
