package ac.weekly.meta.entity;

import ac.weekly.meta.field.FieldMetaData;

/**
 * @author Heli
 */
public record EntityMetaData(
        String name,
        String sourcePackageName,
        String sourceClassName,
        FieldMetaData[] fields) {
}
