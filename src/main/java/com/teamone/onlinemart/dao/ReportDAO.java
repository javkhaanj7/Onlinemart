/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.ReportMainModel;
import com.teamone.onlinemart.models.ReportTopTen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enkhbayar
 */
public class ReportDAO {

    public static List<ReportMainModel> getMain(String vendor_id, String startDate, String endDate) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<ReportMainModel> tops = new ArrayList<>();
        try {
            con = Database.getConnection();

            String sql = "SELECT\n"
                    + "EXTRACT(YEAR_MONTH FROM o.createdAt)\n"
                    + " as year_and_date,\n"
                    + "sum(o.quantity) as total_count, p.price * sum(o.quantity) * (t.vendor_percent/100) as total_amount\n"
                    + "FROM `order`as o  \n"
                    + "LEFT JOIN `product` as p ON o.product_id = p.id\n"
                    + "LEFT JOIN `code_management_table` t ON p.vendor_id = t.vendor_id   \n"
                    + "WHERE EXTRACT(YEAR_MONTH FROM o.createdAt) >= ? and EXTRACT(YEAR_MONTH FROM o.createdAt) <= ? and p.vendor_id = ?\n"
                    + "GROUP BY EXTRACT(YEAR_MONTH FROM o.createdAt) ORDER BY EXTRACT(YEAR_MONTH FROM o.createdAt)";

            ps = con.prepareStatement(sql);
            ps.setString(1, startDate.replace("-", ""));
            ps.setString(2, endDate.replace("-", ""));
            ps.setInt(3, Integer.parseInt(vendor_id));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                tops.add(new ReportMainModel(rs.getString("year_and_date"), rs.getLong("total_count"), rs.getDouble("total_amount")));
            }
        } catch (Exception ex) {
            System.out.println("Error in getMain() -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return tops;
    }

    public static List<ReportTopTen> getTopTen(String vendor_id, String startDate, String endDate) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<ReportTopTen> tops = new ArrayList<>();
        try {
            con = Database.getConnection();

            String sql = "SELECT\n"
                    + "p.id, \n"
                    + "p.name,\n"
                    + "sum(o.quantity) as sold,\n"
                    + "p.price * sum(o.quantity) as amount \n"
                    + "\n"
                    + "FROM `order`as o \n"
                    + "LEFT JOIN `product` as p ON o.product_id = p.id\n"
                    + "WHERE DATE(o.createdAt) >=? and DATE(o.createdAt) <= ? and p.vendor_id = ?\n"
                    + "GROUP BY p.id, p.name, p.price\n"
                    + "ORDER BY sum(o.quantity) desc LIMIT 10";

            ps = con.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setInt(3, Integer.parseInt(vendor_id));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) // found
            {
                tops.add(new ReportTopTen(rs.getString("name"), rs.getLong("sold"), rs.getDouble("amount")));
            }
        } catch (Exception ex) {
            System.out.println("Error in getMain() -->" + ex.getMessage());
        } finally {
            Database.close(con);
        }
        return tops;
    }

//    String sql = "SELECT o.product_id, p.name,\n"
//                    + "EXTRACT(YEAR_MONTH FROM o.createdAt)\n"
//                    + " as year_and_date,\n"
//                    + "sum(o.quantity) as total_count, p.price * sum(o.quantity) as total_amount\n"
//                    + "FROM `order`as o \n"
//                    + "LEFT JOIN `product` as p ON o.product_id = p.id\n"
//                    + "WHERE o.createdAt > ? and o.createdAt < ? and p.vendor_id = ?\n"
//                    + "GROUP BY o.product_id, EXTRACT(YEAR_MONTH FROM o.createdAt) ORDER BY EXTRACT(YEAR_MONTH FROM o.createdAt)";
}
