/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.beans.UserBean;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javkhaa_j7
 */
  
public class UserDAO {
    
    public static int create(UserBean user) {
        int count = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO user (id, user_type, first_name, last_name, vendor_name, email, username, password, address_id) VALUES (NULL,?,?,?,NULL,?,?,?,1)");
            ps.setString(1, "CUSTOMER");
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
            count = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error in create() -->" + ex.getMessage());
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            Database.close(con);
        }
        return count;
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
