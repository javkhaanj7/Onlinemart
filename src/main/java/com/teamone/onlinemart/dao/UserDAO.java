/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.beans.UserBean;
import com.teamone.onlinemart.beans.Util;
import com.teamone.onlinemart.models.User;
import java.sql.*;

/**
 *
 * @author javkhaa_j7
 */
  
public class UserDAO {
    
    public static int create(User user) {
        int count = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            if(con != null) {
                ps = con.prepareStatement("insert into user (user_type, first_name, last_name, email, password) values (?,?,?,?,?)");
                ps.setString(1, user.getUserType());
                ps.setString(2, user.getFirstname());
                ps.setString(3, user.getLastname());
                ps.setString(4, user.getEmail());
                ps.setString(5, Util.MD5(user.getPassword()));
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
    
    public static int update(User user) {
        int count = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            if(con != null) {
                ps = con.prepareStatement("update user set first_name = ?, last_name = ?, email = ?, password = ? where id = ?");
                ps.setString(1, user.getFirstname());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getEmail());
                ps.setString(4, Util.MD5(user.getPassword()));
                ps.setLong(5, user.getId());
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
            ps.setString(2, Util.MD5(password));
  
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
     
     public static User get(String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        User user = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select * from user where email= ? and password= ?");
            ps.setString(1, email);
            ps.setString(2, Util.MD5(password));
  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
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
     
     public static boolean exists(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        UserBean user = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select * from user where email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error in get() -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return false;
    }
     
     public static boolean exists(String email, long id) {
        Connection con = null;
        PreparedStatement ps = null;
        UserBean user = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select * from user where email = ? and id <> ?");
            ps.setString(1, email);
            ps.setLong(2, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error in get() -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return false;
    }
}
