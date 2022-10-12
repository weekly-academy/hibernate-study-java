package org.weekly.core;

import java.sql.Connection;

// TODO: transaction need to implement, but how..?
public interface Session {

    Connection close();

    void persist(Object object);

    void remove(Object object);

    <T> T merge(T object);

    Connection disconnect();

    <T> T get(Class<T> entityType, Object id);
}
