/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.mysql.jdbc.Statement;
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
    public static int save(Product product) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean success = false;
        int id = -1;
        try {
            con = Database.getConnection();
//            stmt = (Statement) con.createStatement();
            ps = con.prepareStatement(
                    "insert into Product(name, description, price, category_id, vendor_id) values(?,?,?,?,?)");
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getCategory_id());
            ps.setInt(5, product.getVendor_id());
  

            ps.executeUpdate();
            
            rs = ps.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()){
                id = rs.getInt(1);
            }
            System.out.println("Last generated ID:" + id);
            
//              stmt.executeUpdate(
//            "INSERT INTO autoIncTutorial (dataField) "
//            + "values ('Can I Get the Auto Increment Field?')",
//            Statement.RETURN_GENERATED_KEYS);
//            
//            rs = ps.getGeneratedKeys();
//            if(rs.next()){
//                id = rs.getInt(1);
//            }                        
            
        } catch (Exception ex) {
            System.out.println("Error to create new product() -->" + ex.getMessage());
            return id;
        } finally {
            Database.close(con);
        }
        return id;
    }
    public static boolean update(Product product) {
        Connection con = null;        
        ResultSet rs = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = Database.getConnection();
            
            ps = con.prepareStatement(
                    "update product set name = ?, description = ?, price = ?, category_id = ?, vendor_id = ? where id = ?  ");
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getCategory_id());
            ps.setInt(5, product.getVendor_id()); //Vendor Id
            ps.setInt(6, product.getId());
  
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
//        DataModel<Product> products = new ArrayDataModel<Product>();        
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product");            
  
            ResultSet rs = ps.executeQuery();            
            while(rs.next()) // found
            {
//                products.setWrappedData((Object)new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price")));
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }              
        } catch (Exception ex) {
            System.out.println("Error in getAll() products -->" + ex.getMessage());            
        } finally {
            Database.close(con);
        }
        return products;
    }
    public static Product getById(int id){
         Connection con = null;
        PreparedStatement ps = null;
        Product product = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product where id = ?");            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();            
            if(rs.next()) // found
            {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id"));                
            }              
        } catch (Exception ex) {
            System.out.println("Error in getAll() products -->" + ex.getMessage());            
        } finally {
            Database.close(con);
        }
        return product;
    }
}
