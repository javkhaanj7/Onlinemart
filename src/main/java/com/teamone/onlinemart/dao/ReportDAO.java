/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.ReportMainModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ichko
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
                    + "sum(o.quantity) as total_count, p.price * sum(o.quantity) as total_amount\n"
                    + "FROM `order`as o  \n"
                    + "LEFT JOIN `product` as p ON o.product_id = p.id\n"
                    + "WHERE o.createdAt >= ? and o.createdAt <= ? and p.vendor_id = ?\n"
                    + "GROUP BY EXTRACT(YEAR_MONTH FROM o.createdAt) ORDER BY EXTRACT(YEAR_MONTH FROM o.createdAt)";

            ps = con.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
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

//    String sql = "SELECT o.product_id, p.name,\n"
//                    + "EXTRACT(YEAR_MONTH FROM o.createdAt)\n"
//                    + " as year_and_date,\n"
//                    + "sum(o.quantity) as total_count, p.price * sum(o.quantity) as total_amount\n"
//                    + "FROM `order`as o \n"
//                    + "LEFT JOIN `product` as p ON o.product_id = p.id\n"
//                    + "WHERE o.createdAt > ? and o.createdAt < ? and p.vendor_id = ?\n"
//                    + "GROUP BY o.product_id, EXTRACT(YEAR_MONTH FROM o.createdAt) ORDER BY EXTRACT(YEAR_MONTH FROM o.createdAt)";
}
