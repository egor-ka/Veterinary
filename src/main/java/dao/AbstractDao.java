package dao;

import exception.SomeException;
import model.Identifiable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 07.11.2016.
 */
public abstract class AbstractDao<T extends Identifiable> implements GenericDao<T> {

    public static final String DB_SCHEMA_NAME = "veterinary";

    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getSelectQuery();

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getUpdateQuery();

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForDelete(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract T getRecordsFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();

    public String getFullTableName() {
        return DB_SCHEMA_NAME + "." + getTableName();
    }

    @Override
    public T getById(int id) throws SomeException {
        String query = String.format("%s WHERE id=?", getSelectQuery());
        List<T> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(getRecordsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
        if (list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new SomeException("GetById - Extracted more than one record(" + list.size() + ")");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws SomeException {
        List<T> list = new ArrayList<>();
        String query = getSelectQuery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(getRecordsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SomeException("getAll - connection problem", e);
        }
        return list;
    }

    @Override
    public int insert(T element) throws SomeException {
        int insertedElementIndex = -1;
        if (element.getId() >= 0 && getById(element.getId()) != null) {
            throw new SomeException("Insert - element with this id already exists.");
        }
        String query = getInsertQuery();
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, element);
            int countRecords = statement.executeUpdate();
            if (countRecords != 1) {
                throw new SomeException("Insert - modified NONE or more than ONE records(" + countRecords + ")");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedElementIndex = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
        return insertedElementIndex;
    }

    @Override
    public void delete(T element) throws SomeException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForDelete(statement, element);
            int countRecords = statement.executeUpdate();
            if (countRecords != 1) {
                throw new SomeException("Delete - modified more than ONE records: " + countRecords);
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
    }

    @Override
    public int update(T element) throws SomeException {
        int updatedElementIndex = -1;
        if (element.getId() < 0 || getById(element.getId()) == null) {
            throw new SomeException("Update - element with this id does not exist.");
        }
        String query = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForUpdate(statement, element);
            int countRecords = statement.executeUpdate();
            if (countRecords != 1) {
                throw new SomeException("Update - modified NONE or more than ONE records(" + countRecords + ")");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                updatedElementIndex = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SomeException(e);
        }
        return updatedElementIndex;
    }

}
