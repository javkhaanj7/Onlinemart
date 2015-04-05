/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.model.Category;
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
    
}
