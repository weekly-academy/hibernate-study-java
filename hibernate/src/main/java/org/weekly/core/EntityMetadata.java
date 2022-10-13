package org.weekly.core;

import org.weekly.type.OrmType;

import java.util.ArrayList;
import java.util.List;

public class EntityMetadata {

    private final String tableName;

    private final List<OrmType> fieldMetadata;

    public String getTableName() {
        return tableName;
    }

    public List<OrmType> getFieldMetadata() {
        return fieldMetadata;
    }

    public EntityMetadata(String tableName) {
        this.fieldMetadata = new ArrayList<>();
        this.tableName = tableName;
    }
}
