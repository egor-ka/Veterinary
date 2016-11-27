package dao;

import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Egor on 06.11.2016.
 */
public class PatientDao extends AbstractDao<Patient> {

    private static String TABLE_NAME = "patients";
    private static String COLUMN_OWNER_NAME = "ownerName";
    private static String COLUMN_PET_NAME = "petName";
    private static String COLUMN_PET_SPECIES = "petSpecies";

    public PatientDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return String.format("SELECT * FROM %s", getFullTableName());
    }

    @Override
    protected String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s, %s) VALUES(?, ?, ?)", getFullTableName(), COLUMN_OWNER_NAME, COLUMN_PET_NAME, COLUMN_PET_SPECIES);
    }

    @Override
    protected String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", getFullTableName());
    }

    @Override
    protected String getUpdateQuery() {
        return String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE id=?", getFullTableName(), COLUMN_OWNER_NAME, COLUMN_PET_NAME, COLUMN_PET_SPECIES);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Patient patient) throws SQLException {
        statement.setString(1, patient.getOwnerName());
        statement.setString(2, patient.getPetName());
        statement.setString(3, patient.getPetSpecies());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Patient patient) throws SQLException {
        statement.setInt(1, patient.getId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Patient patient) throws SQLException {
        statement.setString(1, patient.getOwnerName());
        statement.setString(2, patient.getPetName());
        statement.setString(3, patient.getPetSpecies());
        statement.setInt(4, patient.getId());
    }

    @Override
    protected Patient getRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        return new Patient(resultSet.getInt(1),
                          resultSet.getString(COLUMN_OWNER_NAME),
                          resultSet.getString(COLUMN_PET_NAME),
                          resultSet.getString(COLUMN_PET_SPECIES));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

//
//    @Override
//    public List<Patient> getAll() throws SQLException {
//        String query = String.format("SELECT * FROM %s WHERE id=?", TABLE_NAME);
//        List<Patient> result = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Patient patient = new Patient(resultSet.getInt(0),
//                        resultSet.getString(COLUMN_OWNER_NAME),
//                        resultSet.getString(COLUMN_PET_NAME),
//                        resultSet.getString(COLUMN_PET_SPECIES));
//                result.add(patient);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Patient getById(int id) throws SQLException {
//        String query = String.format("SELECT * FROM %s WHERE id=?", TABLE_NAME);
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                Patient patient = new Patient(resultSet.getInt(0),
//                        resultSet.getString(COLUMN_OWNER_NAME),
//                        resultSet.getString(COLUMN_PET_NAME),
//                        resultSet.getString(COLUMN_PET_SPECIES));
//                return patient;
//            } else {
//                return null;
//            }
//        }
//    }
//
//    @Override
//    public boolean insert(Patient element) throws SQLException {
//        String query = String.format("INSERT INTO %s (%s, %s, %s) VALUES(?, ?, ?)", TABLE_NAME, COLUMN_OWNER_NAME, COLUMN_PET_NAME, COLUMN_PET_SPECIES);
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, element.getOwnerName());
//            statement.setString(2, element.getPetName());
//            statement.setString(3, element.getPetSpecies());
//
//            return statement.executeUpdate() == 1;
//        }
//    }
//
//    @Override
//    public boolean delete(int id) throws SQLException {
//        String query = String.format("DELETE FROM %s WHERE id=?", TABLE_NAME);
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//
//            return statement.executeUpdate() == 1;
//        }
//    }
}
