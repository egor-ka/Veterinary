package dao;

import exception.SomeException;
import model.ClinicalRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 12.11.2016.
 */
public class ClinicalRecordDao extends AbstractDao<ClinicalRecord> {

    private static String TABLE_NAME = "clinicalRecords";
    private static String COLUMN_DOCTOR_ID = "doctorId";
    private static String COLUMN_PATIENT_ID = "patientId";
    private static String COLUMN_PRESCRIPTION = "prescription";
    
    public ClinicalRecordDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return String.format("SELECT * FROM %s", getFullTableName());
    }

    @Override
    protected String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s, %s) VALUES(?, ?, ?)", getFullTableName(), COLUMN_DOCTOR_ID, COLUMN_PATIENT_ID, COLUMN_PRESCRIPTION);
    }

    @Override
    protected String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", getFullTableName());
    }

    @Override
    protected String getUpdateQuery() {
        return String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE id=?", getFullTableName(), COLUMN_DOCTOR_ID, COLUMN_PATIENT_ID, COLUMN_PRESCRIPTION);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ClinicalRecord clinicalRecord) throws SQLException {
        statement.setInt(1, clinicalRecord.getDoctorId());
        statement.setInt(2, clinicalRecord.getPatientId());
        statement.setString(3, clinicalRecord.getPrescription());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, ClinicalRecord clinicalRecord) throws SQLException {
        statement.setInt(1, clinicalRecord.getId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ClinicalRecord clinicalRecord) throws SQLException {
        statement.setInt(1, clinicalRecord.getDoctorId());
        statement.setInt(2, clinicalRecord.getPatientId());
        statement.setString(3, clinicalRecord.getPrescription());
        statement.setInt(4, clinicalRecord.getId());
    }

    @Override
    protected ClinicalRecord getRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        return new ClinicalRecord(resultSet.getInt(1),
                resultSet.getInt(COLUMN_DOCTOR_ID),
                resultSet.getInt(COLUMN_PATIENT_ID),
                resultSet.getString(COLUMN_PRESCRIPTION));
    }
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    public List<List<String>> getAllFullRecords() throws SomeException {
        List<List<String>> list = new ArrayList<>();
        String query = String.format("SELECT CR.id, " +
                "concat(D.firstName, \" \", D.lastName) as fullName, D.specialization, " +
                "P.petSpecies, P.petName, P.ownerName, " +
                "CR.prescription " +
                "FROM veterinary.doctors as D, veterinary.patients as P, %s as CR " +
                "WHERE CR.doctorId=D.id and CR.patientId=P.id", getFullTableName());
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(getFullRecordsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SomeException("getAllFullRecords - connection problem ", e);
        }
        return list;
    }

    private List<String> getFullRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        int count = resultSet.getMetaData().getColumnCount();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= count; ++i) {
            list.add(resultSet.getString(i));
        }
        return list;
    }
}
