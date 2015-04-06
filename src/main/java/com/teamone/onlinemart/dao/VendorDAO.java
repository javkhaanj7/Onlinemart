/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.beans.VendorBean;
import java.sql.*;

/**
 *
 * @author Rabi
 */
public class VendorDAO {

    public static boolean register(VendorBean vendor) {
        Connection con = null;
        int insertId = 0;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO address (id , address, city, state, zipcode, country) VALUES (NULL , ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vendor.getAd().getAddress());
            ps.setString(2, vendor.getAd().getCity());
            ps.setString(3, vendor.getAd().getState());
            ps.setInt(4, vendor.getAd().getZipcode());
            ps.setString(5, vendor.getAd().getCountry());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertId = rs.getInt(1);
                // customerId = String.valueOf(newCustomerId);
            }
            System.out.println("id" + insertId);
            vendor.setUserType("VENDOR");
            ps = con.prepareStatement("INSERT INTO user (id , user_type, first_name, last_name, vendor_name, email, password, address_id) VALUES (NULL , ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, vendor.getUserType());
            ps.setString(2, vendor.getFirstname());
            ps.setString(3, vendor.getLastname());
            ps.setString(4, vendor.getVendorName());
            ps.setString(5, vendor.getEmail());
            ps.setString(6, vendor.getPassword());
            ps.setInt(7, insertId);
            ps.executeUpdate();

            return true;
            // System.out.println(vendor);
            //  System.out.println(vendor.getAd().getCity());
        } catch (Exception ex) {
            System.out.println("Error in Registration -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        // return false;
    }
}
