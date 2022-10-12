package org.weekly.core;

import org.weekly.type.OrmType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JdbcTemplate {

    private final Connection connection;

    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    public <T> T selectOne(Class<T> entityType, String tableName, Object id) throws SQLException {
        Field[] fields = entityType.getDeclaredFields();
        EntityMetadata metadata = Hibernate.getEntityInformation().get(tableName);

        Field idField = Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Id.class) != null)
                .collect(Collectors.toList()).get(0);

        String sql = generateSelectQuery(tableName, idField, id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                T entity = entityType.getConstructor().newInstance();

                for (OrmType type : metadata.getFieldMetadata()) {
                    Field field = entityType.getDeclaredField(type.getColumnName());
                    field.setAccessible(true);
                    field.set(entity, type.getJavaTypeObject(rs));
                }

                return entity;
            }

            return null;
        } catch (RuntimeException | NoSuchFieldException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            // TODO:  handle exception more explicitly
            throw new RuntimeException(e);
        }
    }

    public void delete(String tableName, Object id) {
        throw new RuntimeException("not implement yet");
    }

    public void insert(Object obj) throws IllegalAccessException, SQLException, NoSuchFieldException {
        String tableName = obj.getClass().getAnnotation(Entity.class).name();
        EntityMetadata metadata = Hibernate.getEntityInformation().get(tableName);

        String sql = generateInsertQuery(tableName, metadata);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int idx = 1;
            for (OrmType type : metadata.getFieldMetadata()) {
                Field field = obj.getClass().getDeclaredField(type.getColumnName());
                field.setAccessible(true);
                preparedStatement.setObject(idx++, field.get(obj), type.getJdbcType());
            }

            preparedStatement.executeUpdate();
        }
    }

    private String generateInsertQuery(String tableName, EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(tableName);
        sb.append("(");

        for (OrmType type : metadata.getFieldMetadata()) {
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

    private String generateSelectQuery(String tableName, Field idField, Object id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(tableName);
        sb.append(" where ");
        sb.append(idField.getName());
        sb.append("=");
        sb.append(id);
        return sb.toString();
    }
}
