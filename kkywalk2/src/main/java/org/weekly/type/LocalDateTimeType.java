package org.weekly.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;

public class LocalDateTimeType implements OrmType<LocalDateTime> {

    private final String columnName;

    public LocalDateTimeType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getJdbcType() {
        return Types.BIGINT;
    }

    @Override
    public LocalDateTime getJavaTypeObject(ResultSet rs) throws SQLException {
        return rs.getObject(columnName, LocalDateTime.class);
    }
}