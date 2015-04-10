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
    
    private List<CartItem> items;
    private int quantityToOrder;
    private List<Product> products;
    
    public CartBean() {
        items = new ArrayList<>();
        products = new ArrayList<>();
    }
    
    public void saveItemToOrder(CartItem item) {
        quantityToOrder = 0;
        for(CartItem cartItem : items) {
            quantityToOrder += cartItem.getQuantityToOrder();
        }
    }
    
    public void add(Product product) {
        products.add(product);
    }
    
    public void remove(Product product) {
        products.remove(product);
    }
    
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @return the items
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    /**
     * @return the quantityToOrder
     */
    public int getQuantityToOrder() {
        return quantityToOrder;
    }

    /**
     * @param quantityToOrder the quantityToOrder to set
     */
    public void setQuantityToOrder(int quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }
}
