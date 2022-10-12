package org.weekly;

import org.weekly.core.Hibernate;
import org.weekly.core.JdbcTemplate;
import org.weekly.entity.Student;

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
            JdbcTemplate jdbcTemplate = new JdbcTemplate(conn);

            Student student = jdbcTemplate.selectOne(Student.class, "student", 1);
            System.out.println(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}