/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.CartItem;
import com.teamone.onlinemart.models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author javkhaa_j7
 */
public class OrderDAO {
    
    public static void create(List<CartItem> cartList, long userID){
        Category categoryList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            for(int i = 0; i < cartList.size(); i++){
                ps = con.prepareStatement("insert into `order` (product_id, customer_id, quantity) values (?,?,?)");
                ps.setInt(1, cartList.get(i).getProduct().getId());
                ps.setLong(2, userID);
                ps.setInt(3, cartList.get(i).getQuantity());
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
    }
    
}
