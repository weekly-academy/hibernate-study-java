package ac.weekly.bootstrap.registry;

import ac.weekly.bootstrap.service.ClassLoaderService;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Heli
 */
public class BootstrapServiceRegistry {

    private final ClassLoaderService classLoaderService;

    BootstrapServiceRegistry(ClassLoaderService classLoaderService) {
        validate(classLoaderService);
        this.classLoaderService = classLoaderService;
    }

    private void validate(ClassLoaderService classLoaderService) {
        if (classLoaderService == null) {
            throw new IllegalArgumentException("classLoaderService must not be null");
        }
    }

    public Set<Class<?>> load(Class<? extends Annotation>[] annotatedClasses, Package basePackage) {
        return classLoaderService.findClassesByBasePackage(basePackage)
                .stream()
                .filter(clazz ->
                        Arrays.stream(annotatedClasses).anyMatch(clazz::isAnnotationPresent)
                )
                .collect(Collectors.toSet());
    }
}
