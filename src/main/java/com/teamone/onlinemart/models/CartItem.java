/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teamone.onlinemart.models;

import java.io.Serializable;

/**
 *
 * @author javkhlant
 */
public class CartItem implements Serializable {
    
    private Integer id;
    private Integer quantityToOrder;
    private String name;
    
    public CartItem(Integer id, String name) {
        this.id = id;
        this.name = name;
        quantityToOrder = 0;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartItem other = (CartItem) obj;
        
        if(this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the quantityToOrder
     */
    public Integer getQuantityToOrder() {
        return quantityToOrder;
    }

    /**
     * @param quantityToOrder the quantityToOrder to set
     */
    public void setQuantityToOrder(Integer quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
