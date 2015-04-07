/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.Category;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author javkhaa_j7
 */
public class CategoryDAO {
    
    public static Category[] getAllCategory(){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            
            HashMap<Integer, Category> hList = new HashMap<Integer, Category>();
            ps = con.prepareStatement("select c1.*, c2.name as parentName from category c1 left join category c2 on c1.parent_id=c2.id order by c1.id");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                hList.put(rs.getInt("id"), 
                        new Category(rs.getInt("id"), rs.getInt("parent_id"), rs.getString("parentName"),
                            rs.getString("name"), rs.getString("description")));
            }
            categoryList = new Category[hList.size()];
            int index = 0;
            for(Map.Entry<Integer, Category> mapEntry : hList.entrySet()){
                categoryList[index] = mapEntry.getValue();
                index++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
        return categoryList;
    }
    
    public static Category[] getAllCustomCategory(int exceptId){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            
            HashMap<Integer, Category> hList = new HashMap<Integer, Category>();
            ps = con.prepareStatement("select c1.*, c2.name as parentName from category c1 left join category c2 on c1.parent_id=c2.id where c1.id <> ? order by c1.id");
            ps.setInt(1, exceptId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                hList.put(rs.getInt("id"), 
                        new Category(rs.getInt("id"), rs.getInt("parent_id"), rs.getString("parentName"),
                            rs.getString("name"), rs.getString("description")));
            }
            categoryList = new Category[hList.size()];
            int index = 0;
            for(Map.Entry<Integer, Category> mapEntry : hList.entrySet()){
                categoryList[index] = mapEntry.getValue();
                index++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
        return categoryList;
    }
    
    public static void create(Category category){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            
            ps = con.prepareStatement("insert into category (name, description, parent_id) values (?,?,?)");
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getParentId());
            ps.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
    }
    
    public static void update(Category category){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            
            ps = con.prepareStatement("update category set name = ?, description = ?, parent_id = ? where id = ?");
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getParentId());
            ps.setInt(4, category.getCategoryId());
            ps.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
    }
    
    public static void delete(Category category){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            
            ps = con.prepareStatement("delete from category where id = ?");
            ps.setInt(1, category.getCategoryId());
            ps.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
    }
    
}
