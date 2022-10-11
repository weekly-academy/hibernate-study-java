package org.weekly.core;

import java.io.Serializable;
import java.sql.Connection;

public interface Session {

    Connection close();

    void persist(Object object);

    void delete(Object object);

    Connection disconnect();

    Object get(Class clazz, Serializable id);
}
