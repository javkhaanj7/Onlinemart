/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.mysql.jdbc.Statement;
import com.teamone.onlinemart.models.Category;
import com.teamone.onlinemart.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

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
            if (rs.next()) {
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
        boolean Connectionsuccess = false;
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

    public static ArrayList<Product> getAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Product> products = new ArrayList<>();
//        DataModel<Product> products = new ArrayDataModel<Product>();        
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
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

    public static Product getById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        Product product = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
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

    public static List<Product> findRange(int limit, int offset) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Product> products = new ArrayList<>();
//        DataModel<Product> products = new ArrayDataModel<Product>();        
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product order by id desc limit ? offset ?");

            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
//                products.setWrappedData((Object)new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price")));
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findRange() products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return products;
    }

    public static Product[] findTop(int limit) {
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product order by sold_count desc limit ?");

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findTop(int limit) products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
    }

    public static Product[] findTop() {
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();
        int limit = 10; //Top ten
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product order by sold_count desc limit ?");

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findTop() products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
    }

    public static Product[] findNew(int limit) {
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product order by created desc limit ?");

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findNew(int limit) products -->" + ex.getMessage());
        } finally {
            Database.close(con);        
        }
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
        
    }

    public static Product[] findNew() {
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();
        int limit = 10; //limit
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product order by created desc limit ?");

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findNew(int limit) products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }          
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
    }

    public static void purchaseProduct(int id) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            con = Database.getConnection();

            ps = con.prepareStatement(
                    "update product set sold_count = sold_count+1 where id = ?  ");
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error update product -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
    }
    public static void purchaseProduct(Product product) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            con = Database.getConnection();

            ps = con.prepareStatement(
                    "update product set sold_count = sold_count+1 where id = ?  ");
            ps.setInt(1, product.getId());

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error update product -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
    }
    public static void purchaseProducts(List<Product> products) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String whereFilter = "(";
        StringBuilder sb = new StringBuilder();
        sb.append("(0");
        for (Product p : products) {
            sb.append(",");
            sb.append(p.getId());                    
        }
        sb.append(")");        
        String sql = "update product set sold_count = sold_count+1 WHERE id IN " + sb.toString();
        System.out.println(sql);
        try {
            con = Database.getConnection();

            ps = con.prepareStatement(sql);            

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error purchaseProducts(List<>) -->" + ex.getMessage());            
        } finally {
            Database.close(con);
        }
    }
    public static void purchaseProducts(int[] product_ids) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String whereFilter = "(";
        StringBuilder sb = new StringBuilder();
        sb.append("(0");
        for (int id : product_ids) {
            sb.append(",");
            sb.append(id);                    
        }
        sb.append(")");        
        String sql = "update product set sold_count = sold_count+1 WHERE id IN " + sb.toString();
//        System.out.println(sql);
        try {
            con = Database.getConnection();

            ps = con.prepareStatement(sql);            

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error purchaseProducts() -->" + ex.getMessage());            
        } finally {
            Database.close(con);
        }
    }
    
    public static Product[] findByCategory(int cat_id){
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();        
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product where category_id = ? or category_id in (select id from category where parent_id = ?);");
            ps.setInt(1, cat_id);
            ps.setInt(2, cat_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findByCategory(cat) products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }            
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
    }
    public static Product[] findByCategory(Category cat){
        Connection con = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<>();        
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select * from product where category_id = ? or category_id in (select id from category where parent_id = ?);");
            ps.setInt(1, cat.getCategoryId());
            ps.setInt(2, cat.getCategoryId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("vendor_id")));
            }
        } catch (Exception ex) {
            System.out.println("Error in findByCategory(int id) products -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }            
        Product[] p = new Product[products.size()];
        p = products.toArray(p);
        return p;
    }
}
