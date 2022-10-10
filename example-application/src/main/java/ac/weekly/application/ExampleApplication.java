package ac.weekly.application;

import ac.weekly.bootstrap.BootStrap;
import ac.weekly.manager.EntityManager;
import ac.weekly.meta.entity.EntityMetaData;
import ac.weekly.meta.field.FieldMetaData;

/**
 * @author Heli
 */
public class ExampleApplication {

    public static void main(String[] args) {
        BootStrap.run(ExampleApplication.class.getPackage());

        EntityManager entityManager = EntityManager.get();
        EntityMetaData[] entityMetaData = entityManager.getEntityMetaData();
        for (EntityMetaData entity : entityMetaData) {
            System.out.println("[" + entity.name() + "]");
            System.out.println("> sourcePackageName=" + entity.sourcePackageName());
            System.out.println("> sourceClassName=" + entity.sourceClassName());
            System.out.println("> fields");
            for (FieldMetaData field : entity.fields()) {
                System.out.println("  > " + field);
            }
        }
    }
}
