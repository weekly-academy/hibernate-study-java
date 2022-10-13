package org.weekly.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SessionImpl implements Session {

    private JdbcTemplate jdbcTemplate;

    public SessionImpl(Connection connection) {
        jdbcTemplate = new JdbcTemplate(connection);
    }

    //TODO: need to handle concurrency
    private Map<String, Map<Object, Object>> persistenceMap;

    @Override
    public Connection close() {
        return jdbcTemplate.getConnection();
    }

    @Override
    public void persist(Object object) throws SQLException, NoSuchFieldException, IllegalAccessException {
        jdbcTemplate.insert(object);
        // TODO: put to map if insert success
    }

    @Override
    public void remove(Object object) throws SQLException, IllegalAccessException {
        jdbcTemplate.delete(object);
        // TODO: remove from map if remove success
    }

    @Override
    public <T> T get(Class<T> entityType, Object id) throws SQLException {
        return jdbcTemplate.selectOne(entityType, entityType.getAnnotation(Entity.class).name(), id);
        // TODO: put to map if select success
    }

    @Override
    public void commit() {
        // TODO: autocommit -> off
    }
}
