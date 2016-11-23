package model;

/**
 * Created by Egor on 12.11.2016.
 */
public class Doctor implements Identifiable{

    private int id;
    private String firstName;
    private String lastName;
    private String specialization;

    public Doctor() {
    }

    public Doctor(int id, String firstName, String lastName, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Doctor(String firstName, String lastName, String specialization) {
        this(-1, firstName, lastName, specialization);
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    @Override
    public int hashCode() {
        return firstName.hashCode() + 31 * lastName.hashCode() + 31 * 31 * specialization.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (getClass() != obj.getClass()) return false;

        Doctor doctor = (Doctor) obj;
        return hashCode() == doctor.hashCode()
                && firstName.equals(doctor.firstName)
                && lastName.equals(doctor.lastName)
                && specialization.equals(doctor.getSpecialization());
    }

    @Override
    public String toString() {
        return id + " | " + firstName + " " + lastName + " | " + specialization;
    }
}
