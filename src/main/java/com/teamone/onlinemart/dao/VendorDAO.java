/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.Address;
import com.teamone.onlinemart.models.MyFinance;
import com.teamone.onlinemart.models.Vendor;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rabi
 */
public class VendorDAO {

    public static boolean register(Vendor vendor) {
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

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertId = rs.getInt(1);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                session.setAttribute("registeredId", insertId);
            }

            return true;
        } catch (Exception ex) {
            System.out.println("Error in Registration -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        // return false;
    }

    public boolean makePayment() {
        System.out.println("This is make payment section");
        MyFinance myfinance = null;
        System.out.println(myfinance.getCardType());

        return true;
    }

    public static Vendor[] getAllVendor() {
        Vendor vendorList[] = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();

            HashMap<Integer, Vendor> hList = new HashMap<Integer, Vendor>();
            ps = con.prepareStatement("select us.id, us.user_type, us.first_name, us.last_name, us.vendor_name, us.email, us.password, us.address_id, us.paymentStatus, ad.address, ad.city, ad.state, ad.zipcode, ad.country  from user us, address ad where us.address_id=ad.id user_type=?");
            ps.setString(1, "VENDOR");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                hList.put(rs.getInt("id"),
                        new Vendor(rs.getInt("id"), rs.getString("user_type"), rs.getString("first_name"), rs.getString("last_name"),
                                 rs.getString("email"),rs.getString("vendor_name"), rs.getString("password"), rs.getInt("paymentStatus"),rs.getInt("address_id"),
                                new Address(rs.getInt("address_id"), rs.getString("address"), rs.getString("city"), rs.getString("state"), rs.getInt("zipcode"), rs.getString("country"))));
            }
            vendorList = new Vendor[hList.size()];
            int index = 0;
            for (Map.Entry<Integer, Vendor> mapEntry : hList.entrySet()) {
                vendorList[index] = mapEntry.getValue();
                index++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Database.close(con);
        }
        return vendorList;
    }
}
