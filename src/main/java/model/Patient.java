package model;

/**
 * Created by Egor on 06.11.2016.
 */
public class Patient implements Identifiable {

    private int id;
    private String ownerName;
    private String petName;
    private String petSpecies;

    public Patient(int id, String ownerName, String petName, String petSpecies) {
        this.id = id;
        this.ownerName = ownerName;
        this.petName = petName;
        this.petSpecies = petSpecies;
    }

    public Patient(String ownerName, String petName, String petSpecies) {
        this(0, ownerName, petName, petSpecies);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    @Override
    public int hashCode() {
        return ownerName.hashCode() + 31 * petName.hashCode() + 31 * 31 * petSpecies.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (getClass() != obj.getClass()) return false;
        Patient patient = (Patient) obj;
        return hashCode() == patient.hashCode()
                && ownerName.equals(patient.getOwnerName())
                && petName.equals(patient.getPetName())
                && petSpecies.equals(patient.getPetSpecies());
    }

    @Override
    public String toString() {
        return id + " | " + ownerName + " | " + petName + " | " + petSpecies;
    }
}
