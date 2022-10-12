package org.weekly.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringType implements OrmType<String> {

    private final String columnName;

    public StringType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int getJdbcType() {
        return Types.VARCHAR;
    }

    @Override
    public String getJavaTypeObject(ResultSet rs) throws SQLException {
        return rs.getObject(columnName, String.class);
    }
}
