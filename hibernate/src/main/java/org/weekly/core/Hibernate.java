package org.weekly.core;

import org.reflections.Reflections;
import org.weekly.type.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Hibernate {

    // TODO: get package name from configuration file
    private static final String packageName = "org.weekly";

    private static Map<String, EntityMetadata> entityInformation;

    public static Map<String, EntityMetadata> getEntityInformation() {
        return entityInformation;
    }

    // TODO: in this way can't cover relation between entity
    public static void initEntityInformation() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Entity.class);

        entityInformation = types.stream()
                .map(clazz -> {
                    EntityMetadata entityMetadata = new EntityMetadata(clazz.getAnnotation(Entity.class).name());
                    entityMetadata.getFieldMetadata().addAll(initEntityFieldTypes(clazz.getDeclaredFields()));
                    return entityMetadata;
                })
                .reduce(
                        new HashMap<>(),
                        (acc, element) -> {
                            acc.put(element.getTableName(), element);
                            return acc;
                            },
                        // TODO: what is this?
                        (acc, element) -> acc
                );
        System.out.println(entityInformation);
    }

    private static List<OrmType> initEntityFieldTypes(Field[] fields) {
        return Arrays.stream(fields).map(Hibernate::getOrmTypeFromField).collect(Collectors.toList());
    }

    private static OrmType getOrmTypeFromField(Field field) {
        field.setAccessible(true);
        String typeName = field.getType().getTypeName();
        switch (typeName) {
            case STRING_TYPE:
                return new StringType(field.getName());
            case LOCAL_DATETIME_TYPE:
                return new LocalDateTimeType(field.getName());
            case INT_TYPE:
            case INTEGER_TYPE:
                return new IntegerType(field.getName());
            case LONG_TYPE:
                return new LongType(field.getName());
            default:
                // TODO: define exception type
                throw new RuntimeException("Not supported type");
        }
    }

    private static final String STRING_TYPE = "java.lang.String";
    private static final String INTEGER_TYPE = "java.lang.Integer";
    private static final String INT_TYPE = "int";
    private static final String LOCAL_DATETIME_TYPE = "java.time.LocalDateTime";
    private static final String LONG_TYPE = "java.lang.Long";
}
