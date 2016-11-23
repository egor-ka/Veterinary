package dao;

import exception.ExceptionLogger;
import exception.PasswordException;
import exception.SomeException;
import exception.UsernameException;
import model.Account;
import model.AuthData;
import model.User;

import java.sql.*;

/**
 * Created by Egor on 08.11.2016.
 */
public class AuthDataDao implements Dao{

    private static String FULL_TABLE_NAME = "veterinary.authData";
    private static String COLUMN_USERNAME = "username";
    private static String COLUMN_PASSWORD = "password";

    private Connection connection;

    public AuthDataDao(Connection connection) {
        this.connection = connection;
    }

    public AuthData get(String username) throws SomeException {
        AuthData authData = null;
        String sql = String.format("SELECT * FROM %s WHERE %s=?", FULL_TABLE_NAME, COLUMN_USERNAME);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                authData = new AuthData(resultSet.getInt(1), resultSet.getString(COLUMN_USERNAME), resultSet.getString(COLUMN_PASSWORD));
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
        return authData;
    }

    public void register(String username, String password, String firstName, String lastName)
            throws UsernameException {
        Savepoint savepoint = null;
        if (isUsernameValid(username)) {
            throw new UsernameException("Username already exists.");
        }
        try {
            savepoint = connection.setSavepoint();
            AuthData authData = new AuthData(username, password);
            insert(authData);

            UserDao userDao = new UserDao(connection);
            User user = new User(firstName, lastName);
            int insertedElementIndex = userDao.insert(user);

            int authDataId = get(username).getId();
            AccountDao accountDao = new AccountDao(connection);
            Account account = new Account(insertedElementIndex, authDataId);
            accountDao.insert(account);
        } catch (SomeException e) {
            if (savepoint != null) {
                try {
                    connection.rollback(savepoint);
                } catch (SQLException ex) {
                    ExceptionLogger.rollBackException("Register", ex);
                }
            }
        } catch (SQLException e) {
            ExceptionLogger.connectionException("Register", e);
        }
    }

    public AuthData authorize(String username, String password) throws UsernameException, PasswordException {
        try {
            AuthData authData = get(username);
            if (authData == null) {
                throw new UsernameException("Username does not exist");
            }
            if (password == null) {
                throw new PasswordException("Incorrect password");
            }
            if (password.equals(authData.getPassword())) {
                return authData;
            } else {
                throw new PasswordException("Incorrect password");
            }
        } catch (SomeException e) {
            throw new UsernameException("Username does not exist");
        }
    }

    private boolean isUsernameValid(String username) {
        try {
            return (get(username) != null);
        } catch (SomeException e) {
            return false;
        }
    }

    private void insert(AuthData authData) throws SomeException {
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES(?, ?)", FULL_TABLE_NAME, COLUMN_USERNAME, COLUMN_PASSWORD);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, authData.getUsername());
            statement.setString(2, authData.getPassword());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SomeException("Insert - modified NONE or more than ONE records(" + count + ")");
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
    }
}
