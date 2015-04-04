/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.User;
import java.sql.*;

/**
 *
 * @author javkhaa_j7
 */
  
public class UserDAO {
    
    public static boolean create(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "insert into customer (firstname, lastname, email, phone, username, password) values (?,?,?,?,?,?)");
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
  
            return ps.execute();
        } catch (SQLException ex) {
            System.out.println("Error in create() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
    }
    
     public static boolean login(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select email, password from user where email= ? and password= ? ");
            ps.setString(1, user);
            ps.setString(2, password);
  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                System.out.println(rs.getString("email"));
                return true;
            }
            else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
    }
}
