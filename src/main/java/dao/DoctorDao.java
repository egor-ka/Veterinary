package dao;

import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Egor on 12.11.2016.
 */
public class DoctorDao extends AbstractDao<Doctor> {

    private static final String TABLE_NAME = "doctors";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_SPECIALIZATION = "specialization";

    public DoctorDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return String.format("SELECT * FROM %s", getFullTableName());
    }

    @Override
    protected String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s, %s) VALUES(?, ?, ?)", getFullTableName(), COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_SPECIALIZATION);
    }

    @Override
    protected String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", getFullTableName());
    }

    @Override
    protected String getUpdateQuery() {
        return String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE id=?", getFullTableName(), COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_SPECIALIZATION);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Doctor doctor) throws SQLException {
        statement.setString(1, doctor.getFirstName());
        statement.setString(2, doctor.getLastName());
        statement.setString(3, doctor.getSpecialization());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Doctor doctor) throws SQLException {
        statement.setInt(1, doctor.getId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Doctor doctor) throws SQLException {
        statement.setString(1, doctor.getFirstName());
        statement.setString(2, doctor.getLastName());
        statement.setString(3, doctor.getSpecialization());
        statement.setInt(3, doctor.getId());
    }

    @Override
    protected Doctor getRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        return new Doctor(resultSet.getInt(1),
                resultSet.getString(COLUMN_FIRST_NAME),
                resultSet.getString(COLUMN_LAST_NAME),
                resultSet.getString(COLUMN_SPECIALIZATION));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
