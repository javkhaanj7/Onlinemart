package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.MyFinance;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rabi
 */
public class MyfinanceDAO {

    public static boolean makePayment(MyFinance myfinance) {
        // operations to do payment in myfinance dao
        System.out.println("This is DAO Myfinance ");
        System.out.println("Card Name from form" + myfinance.getHolderName());
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select * from myfinance where holderName=? and number=?  and securityCode=? and cardType=?");
            ps.setString(1, myfinance.getHolderName());
            ps.setString(2, myfinance.getNumber());
            // ps.setDate(3, (Date) myfinance.getExpiryDate());
            ps.setInt(3, myfinance.getSecurityCode());
            ps.setString(4, myfinance.getCardType());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //System.out.println("Record Exists");
                // System.out.println();
                if (rs.getDouble("balance") > 10000) {

                    // deduct paied amont from balance
                    ps = con.prepareStatement("update myfinance set balance=? where number=?");
                    ps.setDouble(1, rs.getDouble("balance") - 10000);
                    ps.setString(2, rs.getString("number"));
                    ps.executeUpdate();

                    // update payment status table for the record
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = dateFormat.format(new Date());

                    ps = con.prepareStatement("insert into paymentStatus set cardId=?, userId=?, amount=?, date=?");

                    ps.setInt(1, rs.getInt("id"));
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                    ps.setInt(2, (int) session.getAttribute("registeredId"));
                    ps.setDouble(3, 10000);
                    ps.setString(4, date);
                    ps.executeUpdate();

                    // update user/vendor table to set payment status 1
                    ps = con.prepareStatement("update user set paymentStatus=? where id=?");
                    ps.setString(1, "1");
                    ps.setInt(2, (int) session.getAttribute("registeredId"));
                    ps.executeUpdate();

                } else {
                    return false;
                }
            } else {
                System.out.println("No Card Found");
                return false;
                //   System.out.println(rs.getInt('id'));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            Database.close(con);
        }
        return true;
    }

}
