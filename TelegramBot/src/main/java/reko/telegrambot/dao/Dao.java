package reko.telegrambot.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for dao
 * 
 * @author xrexrexr
 * @param <T> The object used
 * @param <K> The id of the object in database
 */
public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    ArrayList<T> findAll() throws SQLException;

    T save(T object) throws SQLException;

    void delete(K key) throws SQLException;
}
