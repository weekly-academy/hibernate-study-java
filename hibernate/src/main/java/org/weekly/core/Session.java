package org.weekly.core;

import java.sql.Connection;
import java.sql.SQLException;

public interface Session {

    Connection close();

    void persist(Object object) throws SQLException, NoSuchFieldException, IllegalAccessException;

    void remove(Object object) throws SQLException, IllegalAccessException;

    <T> T get(Class<T> entityType, Object id) throws SQLException;

    // TODO: transaction need to implement, but how..?
    void commit();
}
