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
public class ReportTopTen {
    private String product_name;
    private long sold_quantity;
    private double amount;

    public ReportTopTen(String product_name, long sold_quantity, double amount) {
        this.product_name = product_name;
        this.sold_quantity = sold_quantity;
        this.amount = amount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(long sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
