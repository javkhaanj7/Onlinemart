/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.CategoryDAO;
import com.teamone.onlinemart.model.Category;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

/**
 *
 * @author javkhaa_j7
 */

@ManagedBean(name="category")
@RequestScoped
public class CategoryBean implements Serializable {
    
    private String selectedCategory;

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    
    private DataModel<Category> category = new ArrayDataModel<Category>(CategoryDAO.getAllCategory());
    
    public DataModel<Category> getCategoryList(){
        return category;
    }
    
}
