package org.weekly.core;

import org.reflections.Reflections;
import org.weekly.type.IntegerType;
import org.weekly.type.LocalDateTimeType;
import org.weekly.type.LongType;
import org.weekly.type.StringType;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Map;
import java.util.Set;

public class Hibernate {

    // TODO: get package name from configuration file
    private static String packageName = "org.weekly";

    private static Map<String, EntityMetadata> entityInformation;

    // TODO: in this way can't cover relation between entity
    public static void initEntityInformation() {
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Entity.class);
        for(Class<?> clazz : types) {
            EntityMetadata entityMetadata = new EntityMetadata(clazz.getAnnotation(Entity.class).name());

            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                switch (clazz.getTypeName()) {
                    case STRING_TYPE:
                        entityMetadata.getFieldMetadata().add(new StringType(clazz.getName()));
                    case LOCAL_DATETIME_TYPE:
                        entityMetadata.getFieldMetadata().add(new LocalDateTimeType(clazz.getName()));
                        break;
                    case INTEGER_TYPE:
                        entityMetadata.getFieldMetadata().add(new IntegerType(clazz.getName()));
                        break;
                    case LONG_TYPE:
                        entityMetadata.getFieldMetadata().add(new LongType(clazz.getName()));
                        break;
                }
            }
        }
    }

    private static final String STRING_TYPE = "java.lang.String";
    private static final String INTEGER_TYPE = "java.lang.Integer";
    private static final String LOCAL_DATETIME_TYPE = "java.time.LocalDateTime";
    private static final String LONG_TYPE = "java.lang.Long";
}
