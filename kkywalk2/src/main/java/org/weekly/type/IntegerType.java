package org.weekly.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerType implements OrmType<Integer>{

    private final String columnName;

    public IntegerType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int getJdbcType() {
        return Types.INTEGER;
    }

    @Override
    public Integer getJavaTypeObject(ResultSet rs) throws SQLException {
        return rs.getObject(columnName, Integer.class);
    }
}
