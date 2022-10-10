package ac.weekly.bootstrap;

import ac.weekly.bootstrap.registry.BootstrapServiceRegistry;
import ac.weekly.bootstrap.registry.BootstrapServiceRegistryBuilder;
import ac.weekly.bootstrap.service.ClassLoaderService;
import ac.weekly.manager.EntityManager;
import ac.weekly.meta.entity.Entity;
import ac.weekly.meta.entity.EntityMetaData;
import ac.weekly.meta.field.Column;
import ac.weekly.meta.field.FieldMetaData;
import ac.weekly.meta.source.MetaDataSources;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Heli
 */
public final class BootStrap {

    private static final BootStrap INSTANCE;

    static {
        INSTANCE = new BootStrap();
    }

    public static void run(Package basePackage) {
        INSTANCE.initialize(basePackage);
    }

    private void initialize(Package basePackage) {
        BootstrapServiceRegistry registry = new BootstrapServiceRegistryBuilder()
                .classLoader(new ClassLoaderService())
                .build();

        MetaDataSources metaDataSources = new MetaDataSources(registry)
                .addAnnotatedClasses(Entity.class)
                .basePackage(basePackage);

        EntityMetaData[] entityMetaData = load(metaDataSources);
        for (EntityMetaData metaData : entityMetaData) {
            EntityManager entityManager = EntityManager.get();
            entityManager.addEntityMetaData(metaData);
        }
    }

    private EntityMetaData[] load(MetaDataSources metaDataSources) {
        BootstrapServiceRegistry registry = metaDataSources.getRegistry();

        Set<Class<?>> loadedClasses = registry.load(
                metaDataSources.getAnnotatedClasses(),
                metaDataSources.getBasePackage()
        );

        return loadedClasses.stream()
                .map(loadedClass -> {
                    Entity entity = loadedClass.getAnnotation(Entity.class);
                    return new EntityMetaData(
                            entity.name().isBlank()
                                    ? loadedClass.getSimpleName()
                                    : entity.name(),
                            loadedClass.getPackageName(),
                            loadedClass.getSimpleName(),
                            parseFields(loadedClass)
                    );
                })
                .toArray(EntityMetaData[]::new);
    }

    private FieldMetaData[] parseFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> {
                    Column column = field.getAnnotation(Column.class);
                    return new FieldMetaData(
                            column.name().isBlank()
                                    ? field.getName()
                                    : column.name(),
                            field.getType()
                    );
                })
                .toArray(FieldMetaData[]::new);
    }
}
