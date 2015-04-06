/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.ProductDAO;
import com.teamone.onlinemart.models.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ichko
 */
@ManagedBean
@SessionScoped
public class ProductBean implements Serializable {

    private Product product;    

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    private List<Product> products = new ArrayList<Product>();

    public ProductBean() {
        product = new Product();
        products = new ArrayList<Product>();
        itemIndex = 0;
    }
    
    private int itemIndex;

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    private void getList() {
      setProducts(ProductDAO.getAll());

    }
    //-------------------------------
    public String list(){        
        getList();
        return "/product/list";
    }
    

    public String prepareCreate() {
        setProduct(new Product());
        return "Create";
    }
    

    public String create() {        
        ProductDAO.save(product);
        getList();
        return "/product/list";
    }
    
    public String add(){
        product = new Product();
        return "/product/add";
    }

    public String delete(Product p) {
        ProductDAO.delete(p.getId());
        getList();
        return "/product/list";
    }
    
    public String edit(Product p){
        this.product = p;
        return "/product/edit";
    }
    
    public String editUpdate(){
        ProductDAO.update(product);
        getList();
        return "/product/list";
    }
}
