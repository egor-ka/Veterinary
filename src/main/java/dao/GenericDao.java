package dao;

import exception.SomeException;

import java.util.List;

/**
 * Created by Egor on 06.11.2016.
 */
public interface GenericDao<T> extends Dao{

    List<T> getAll() throws SomeException;

    T getById(int id) throws SomeException;

    int insert(T element) throws SomeException;

    void delete(T element) throws SomeException;

    int update(T element) throws SomeException;
}
