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
    public void register(UserModel mdl){
        String sql = "INSERT INTO tbl_users (username, password) VALUES "
                + "(?,?)";
        
          try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             preparedStatement.setString(1, mdl.getUsername());
             preparedStatement.setString(2, mdl.getPassword());
             
             preparedStatement.executeUpdate ();
                     
             JOptionPane.showMessageDialog(null,"Account Added Successfully");
          } catch (SQLException e) {
              System.out.println("Error inserting data: " + e.getMessage());
              e.printStackTrace();
          }
        
    }
    
    
    public boolean authenticate(String username, String password){
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
    
    public List<UserModel> userDetail(String username, String password){
        UserModel mdl = new UserModel();
        List<UserModel> list = new ArrayList<UserModel>();
        String sql = "Select * from tbl_users WHERE username = ? AND password = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        mdl.setId(resultSet.getInt("id"));
                        mdl.setUsername(resultSet.getString("username"));
                    }
                    list.add(mdl);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
