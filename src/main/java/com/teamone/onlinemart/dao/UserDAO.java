/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.beans.UserBean;
import java.sql.*;

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
            if(con != null) {
                ps = con.prepareStatement("INSERT INTO user (user_type, first_name, last_name, email, password) VALUES (?,?,?,?,?)");
                ps.setString(1, user.getUserType());
                ps.setString(2, user.getFirstname());
                ps.setString(3, user.getLastname());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPassword());
                count = ps.executeUpdate();
            }
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
     
     public static UserBean get(String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        UserBean user = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select * from user where email= ? and password= ?");
            ps.setString(1, email);
            ps.setString(2, password);
  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));
                user.setUserType(rs.getString("user_type"));
            }
        } catch (Exception ex) {
            System.out.println("Error in get() -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return user;
     }
}
