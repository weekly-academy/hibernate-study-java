package org.weekly.type;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OrmType<T> {

    int getJdbcType();

    T getJavaTypeObject(ResultSet rs) throws SQLException;
}
