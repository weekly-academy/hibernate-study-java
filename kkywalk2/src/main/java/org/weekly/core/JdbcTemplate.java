package org.weekly.core;

import org.weekly.type.OrmType;

import java.lang.reflect.Field;
import java.sql.*;

public class JdbcTemplate {

    private final Connection connection;

    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    public Object selectOne() {
        throw new RuntimeException("not implement yet");
    }

    public void delete(String tableName, Object id) {
        throw new RuntimeException("not implement yet");
    }

    public void insert(Object obj) throws IllegalAccessException, SQLException, NoSuchFieldException {
        String tableName = obj.getClass().getAnnotation(Entity.class).name();
        EntityMetadata metadata = Hibernate.getEntityInformation().get(tableName);

        String sql = generateInsertQuery(tableName, metadata);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        int idx = 1;
        for(OrmType type : metadata.getFieldMetadata()) {
            Field field = obj.getClass().getDeclaredField(type.getColumnName());
            field.setAccessible(true);
            preparedStatement.setObject(idx++, field.get(obj), type.getJdbcType());
        }

        preparedStatement.executeUpdate();
    }

    private String generateInsertQuery(String tableName, EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into "); sb.append(tableName); sb.append("(");

        for(OrmType type : metadata.getFieldMetadata()) {
            sb.append(type.getColumnName());
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES(");
        sb.append("?,".repeat(metadata.getFieldMetadata().size()));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }
}
