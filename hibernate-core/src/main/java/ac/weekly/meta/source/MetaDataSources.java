package ac.weekly.meta.source;

import ac.weekly.bootstrap.registry.BootstrapServiceRegistry;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author Heli
 */
public class MetaDataSources {

    private final BootstrapServiceRegistry registry;
    private Class<? extends Annotation>[] annotatedClasses;
    private Package basePackage;

    public MetaDataSources(BootstrapServiceRegistry registry) {
        this.registry = registry;
    }

    @SafeVarargs
    public final MetaDataSources addAnnotatedClasses(Class<? extends Annotation>... annotations) {
        this.annotatedClasses = Arrays.copyOf(annotations, annotations.length);
        return this;
    }

    public MetaDataSources basePackage(Package basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    public BootstrapServiceRegistry getRegistry() {
        return registry;
    }

    public Class<? extends Annotation>[] getAnnotatedClasses() {
        return annotatedClasses;
    }

    public Package getBasePackage() {
        return basePackage;
    }
}
