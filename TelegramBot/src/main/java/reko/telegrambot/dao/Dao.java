package reko.telegrambot.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    ArrayList<T> findAll() throws SQLException;

    T save(T object) throws SQLException;

    void delete(K key) throws SQLException;
}
