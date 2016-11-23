package model;

/**
 * Created by Egor on 12.11.2016.
 */
public class ClinicalRecord implements Identifiable {

    private int id;
    private int doctorId;
    private int patientId;
    private String prescription;

    public ClinicalRecord(int id, int doctorId, int patientId, String prescription) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.prescription = prescription;
    }

    public ClinicalRecord(int doctorId, int patientId, String prescription) {
        this(-1, doctorId, patientId, prescription);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
