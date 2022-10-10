package ac.weekly.bootstrap.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Heli
 */
public class ClassLoaderService {

    public Set<Class<?>> findClassesByBasePackage(Package basePackage) {
        String packageName = basePackage.getName();
        String packageDirectory = packageName.replaceAll("[.]", "/");

        Set<Class<?>> classes = new HashSet<>();
        try {
            Enumeration<URL> urls = ClassLoader.getSystemClassLoader()
                    .getResources(packageDirectory);

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                File directory = new File(url.getFile());

                if (!directory.exists()) {
                    return Collections.emptySet();
                }

                try (Stream<Path> walk = Files.walk(directory.toPath())) {
                    Set<? extends Class<?>> loadedClasses = walk
                            .filter(path -> path.toString().endsWith(".class"))
                            .map(path -> {
                                String fullyQualifierClassName = packageName +
                                        path.toString()
                                                .substring(0, path.toString().length() - 6)
                                                .replace(directory.toString(), "")
                                                .replace("/", ".");
                                return getClass(fullyQualifierClassName);
                            })
                            .collect(Collectors.toSet());
                    classes.addAll(loadedClasses);
                }
            }
        } catch (IOException ignored) {
            // ignored
        }
        return classes;
    }

    private Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
