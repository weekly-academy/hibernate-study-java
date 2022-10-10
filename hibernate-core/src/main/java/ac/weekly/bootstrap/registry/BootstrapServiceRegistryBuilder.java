package ac.weekly.bootstrap.registry;

import ac.weekly.bootstrap.service.ClassLoaderService;

/**
 * @author Heli
 */
public class BootstrapServiceRegistryBuilder {

    private ClassLoaderService classLoaderService = null;

    public BootstrapServiceRegistryBuilder classLoader(final ClassLoaderService classLoaderService) {
        this.classLoaderService = classLoaderService;
        return this;
    }

    public BootstrapServiceRegistry build() {
        return new BootstrapServiceRegistry(classLoaderService);
    }
}
