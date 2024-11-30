/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_task_manager";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
