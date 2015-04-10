/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.models.CartItem;
import com.teamone.onlinemart.models.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author javkhlant
 */
@ManagedBean(name = "cartBean")
@SessionScoped
public class CartBean implements Serializable {
    
    private int productId;
    private int quantity;
    private List<CartItem> items;
    
    public CartBean() {
        items = new ArrayList<>();
    }
    
    public int totalProducts() {
        int sum = 0;
        for(CartItem item : items) {
            sum = sum + item.getQuantity();
        }
        return sum;
    }
    
    public String addItem() {
        if(getQuantity() > 0) {
            //select
        }
        return "home";
    }
    
    public List<CartItem> getCartContent() {
        return this.items;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
