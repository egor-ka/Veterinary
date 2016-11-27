package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Egor on 08.11.2016.
 */
public class UserDao extends AbstractDao<User> {  
    
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return String.format("SELECT * FROM %s", getFullTableName());
    }

    @Override
    protected String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s) VALUES(?, ?)", getFullTableName(), COLUMN_FIRST_NAME, COLUMN_LAST_NAME);
    }

    @Override
    protected String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", getFullTableName());
    }

    @Override
    protected String getUpdateQuery() {
        return String.format("UPDATE %s SET %s=?, %s=? WHERE id=?", getFullTableName(), COLUMN_FIRST_NAME, COLUMN_LAST_NAME);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, User user) throws SQLException {
        statement.setInt(1, user.getId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getId());
    }

    @Override
    protected User getRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(1),
                resultSet.getString(COLUMN_FIRST_NAME),
                resultSet.getString(COLUMN_LAST_NAME));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

/*
    public User get(String username) throws SomeException {
        String query = String.format("SELECT USERS.*, DATA.username FROM %s AS USERS " +
                "INNER JOIN " + DB_SCHEMA_NAME + ".users as A " +
                "ON USERS.id = A.userId INNER JOIN " + DB_SCHEMA_NAME + ".authData as DATA " +
                "ON A.loginId = DATA.id WHERE DATA.username=?;", getFullTableName(), );
        List<User> list = get(username, query);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new SomeException("GetById - extracted more than ONE record(" + list.size() + ")");
        }
        return list.iterator().next();
    }*/
}
