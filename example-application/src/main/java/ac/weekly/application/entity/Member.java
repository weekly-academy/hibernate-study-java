package ac.weekly.application.entity;

import ac.weekly.meta.entity.Entity;
import ac.weekly.meta.field.Column;

/**
 * @author Heli
 */
@Entity(name = "User")
public class Member {

    private Long id;

    @Column(name = "displayName")
    private String name;

    private int age;
}
