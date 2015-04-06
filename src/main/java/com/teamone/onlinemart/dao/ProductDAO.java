/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Ichko
 */
public class ProductDAO {
    public static boolean save(Product product) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "insert into Product(name, description, price) values(?,?,?)");
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
  
//            ps.executeQuery();
            ps.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error to create new product() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        return true;
    }
    public static boolean update(Product product) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "update product set name = ?, description = ?, price = ? where id = ?  ");
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getId());
  
            ps.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error update product -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        return true;
    }
    public static boolean delete(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "delete from product where id = ?  ");
            ps.setInt(1, id);            
  
            ps.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error delete product -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        return true;
    }
    
    public static ArrayList<Product> getAll(){
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select id, name, description, price from product");            
  
            ResultSet rs = ps.executeQuery();            
            while(rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price")));
            }  
        } catch (Exception ex) {
            System.out.println("Error in getAll() products -->" + ex.getMessage());            
        } finally {
            Database.close(con);
        }
        return products;
    }
}
