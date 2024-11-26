/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import model.UserModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
        private static final String URL = "jdbc:mysql://localhost:3306/db_task_manager";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        private static Connection connection;
        private DatabaseConnection() {
        }
        public static Connection getConnection(){
            if (connection == null){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    
                    connection = DriverManager.getConnection( URL, USER, PASSWORD);
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Error establishing database connection");
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }

