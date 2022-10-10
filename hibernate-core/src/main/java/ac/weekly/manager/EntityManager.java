package ac.weekly.manager;

import ac.weekly.meta.entity.EntityMetaData;

import java.util.Arrays;

/**
 * @author Heli
 */
public class EntityManager {

    private static final EntityManager INSTANCE;

    static {
        INSTANCE = new EntityManager();
    }

    private EntityMetaData[] entityMetaData = new EntityMetaData[0];

    public static EntityManager get() {
        return INSTANCE;
    }

    public void addEntityMetaData(EntityMetaData entityMetaData) {
        int currentMetaDataLength = this.entityMetaData.length;
        this.entityMetaData = Arrays.copyOf(this.entityMetaData, currentMetaDataLength + 1);
        this.entityMetaData[currentMetaDataLength] = entityMetaData;
    }

    public EntityMetaData[] getEntityMetaData() {
        return entityMetaData;
    }
}
