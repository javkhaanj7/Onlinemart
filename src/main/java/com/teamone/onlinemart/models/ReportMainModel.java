/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.models;

/**
 *
 * @author Enkhbayar
 */
public class ReportMainModel {

    private String date;
    private long total_quantity;
    private double total_amount;
    
    public ReportMainModel(String date, long total_quantity, double total_amount) {
        this.date = date.substring(0, 4)+ "-" + date.substring(4);
        this.total_quantity = total_quantity;
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(long total_quantity) {
        this.total_quantity = total_quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

}
