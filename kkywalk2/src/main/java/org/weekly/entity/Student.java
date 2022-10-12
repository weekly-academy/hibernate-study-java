package org.weekly.entity;

import org.weekly.core.Entity;
import org.weekly.core.Id;

@Entity(name = "student")
public class Student {

    @Id
    int id;
    String name;
    int grade;

    public Student() {

    }

    public Student(int id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
}
