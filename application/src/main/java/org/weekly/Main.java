package org.weekly;

import org.weekly.entity.Student;
import org.weekly.core.Hibernate;
import org.weekly.core.SessionImpl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static String DB_URL = "jdbc:mysql://localhost:52312/testdb?serverTimezone=Asia/Seoul&useSSL=false";
    private static String USER = "root";
    private static String PASS = "1234";

    public static void main(String[] args) {

        Hibernate.initEntityInformation();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            SessionImpl session = new SessionImpl(conn);

            Student student = new Student(1, "kky", 6);
            session.persist(student);
            session.remove(student);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}