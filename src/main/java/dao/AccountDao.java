package dao;

import exception.SomeException;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 07.11.2016.
 */
public class AccountDao extends AbstractDao<Account> {

    private static String TABLE_NAME = "accounts";
    private static String COLUMN_USER_ID = "userId";
    private static String COLUMN_AUTH_DATA_ID = "authDataId";

    public AccountDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return String.format("SELECT * FROM %s", getFullTableName());
    }

    @Override
    protected String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s) VALUES(?, ?)", getFullTableName(), COLUMN_USER_ID, COLUMN_AUTH_DATA_ID);
    }

    @Override
    protected String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", getFullTableName());
    }

    @Override
    protected String getUpdateQuery() {
        return String.format("UPDATE %s SET %s=?, %s=? WHERE id=?", getFullTableName(), COLUMN_USER_ID, COLUMN_AUTH_DATA_ID);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Account account) throws SQLException {
        statement.setInt(1, account.getUserId());
        statement.setInt(2, account.getAuthDataId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Account account) throws SQLException {
        statement.setInt(1, account.getId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Account account) throws SQLException {
        statement.setInt(1, account.getUserId());
        statement.setInt(2, account.getAuthDataId());
        statement.setInt(3, account.getId());
    }

    @Override
    protected Account getRecordsFromResultSet(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getInt(1),
                resultSet.getInt(COLUMN_USER_ID),
                resultSet.getInt(COLUMN_AUTH_DATA_ID));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
