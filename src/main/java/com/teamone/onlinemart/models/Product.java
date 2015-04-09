/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.models;

import java.io.Serializable;

/**
 *
 * @author Ichko
 */
public class Product implements Serializable{
    private int id;
    private String name;
    private String description;
    private double price;
    private int category_id;
    private int vendor_id;

    public Product(){
        
    }
    public Product(String name, String desc, double price){
        this.name = name;
        this.description = desc;
        this.price = price;
    }
    public Product(int id, String name, String description, double price, int category_id, int vendor_id, String image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.vendor_id = vendor_id;
        this.imagePath = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }
    
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", category_id=" + category_id + ", vendor_id=" + vendor_id + '}';
    }
    
    
}
