package org.weekly.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LongType implements OrmType<Long> {

    private final String columnName;

    public LongType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int getJdbcType() {
        return Types.BIGINT;
    }

    @Override
    public Long getJavaTypeObject(ResultSet rs) throws SQLException {
        return rs.getObject(columnName, Long.class);
    }
}
