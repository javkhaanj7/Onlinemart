/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.ProductDAO;
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
    private boolean updated;
    
    public CartBean() {
        items = new ArrayList<>();
    }
    
    public int totalProducts() {
        int sum = 0;
        for(CartItem item : items) {
            sum += item.getQuantity();
        }
        return sum;
    }
    
    public double totalAmount() {
        double sum = 0;
        for(CartItem item : items) {
            sum += item.getProduct().getPrice() * item.getQuantity();
        }
        return sum;
    }
    
    public String addItem() {
        if(getQuantity() >= 0) {
            if(getQuantity() == 0) setQuantity(1);
            // Get product equipped with the id
            Product product = ProductDAO.getById(getProductId());
            // Does the item already exist?
            boolean exists = false;
            for(CartItem item : items) {
                if(item.getProduct().getId() == product.getId()) {
                    item.incementQuantity(getQuantity());
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                items.add(new CartItem(product, getQuantity()));
            }
            setUpdated(true);
        }
        // Reset params
        setProductId(0);
        setQuantity(0);
        return "";
    }
    
    public String updateItem() {
        if(getQuantity() > 0) {
            // Get product equipped with the id
            Product product = ProductDAO.getById(getProductId());
            for(CartItem item : items) {
                if(item.getProduct().getId() == product.getId()) {
                    // Exists
                    item.setQuantity(getQuantity());
                    break;
                }
            }
            setUpdated(true);
        }
        // Reset params
        setProductId(0);
        setQuantity(0);
        return "";
    }
    
    public String removeProduct(int id) {
        Product product = ProductDAO.getById(id);
        CartItem itemToBeRemoved = null;
        for(CartItem item : items) {
            if(item.getProduct().getId() == product.getId()) {
                // Exists
                itemToBeRemoved = item;
                break;
            }
        }
        items.remove(itemToBeRemoved);
        setUpdated(true);
        return "";
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

    /**
     * @return the updated
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
