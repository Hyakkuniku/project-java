/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.UserModel;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UserController {
        Connection connection = DatabaseConnection.getConnection();

        public boolean register(UserModel mdl) {
        String sql = "INSERT INTO tbl_users (username, password, role_id) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mdl.getUsername());
            preparedStatement.setString(2, mdl.getPassword());
            preparedStatement.setInt(3, mdl.getRoleId());  // Insert role_id instead of role_name

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Account Added Successfully");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

        
    public int getRoleIdByName(String roleName) {
        String sql = "SELECT role_id FROM roles WHERE role_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("role_id");
            } else {
                return -1; // Role not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


        public boolean authenticate(String username, String password) {
            try {
                String sql = "SELECT * FROM tbl_users WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.next();
            } catch (SQLException e) {
                System.out.println("Error fetching data: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public List<UserModel> userDetail(String username, String password) {
        UserModel mdl = new UserModel();
        List<UserModel> list = new ArrayList<>();

        String sql = "SELECT u.id, u.username, u.password, u.role_id, r.role_name "
                + "FROM tbl_users u "
                + "LEFT JOIN roles r ON u.role_id = r.role_id "
                + "WHERE u.username = ? AND u.password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    mdl.setId(resultSet.getInt("id"));
                    mdl.setUsername(resultSet.getString("username"));
                    mdl.setPassword(resultSet.getString("password"));
                    mdl.setRoleId(resultSet.getInt("role_id"));
                    mdl.setRole(resultSet.getString("role_name")); // Ensure this is properly set
                }
                list.add(mdl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    }
