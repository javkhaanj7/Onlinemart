/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.CategoryDAO;
import com.teamone.onlinemart.models.Category;
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
    
    private Category category;

    public CategoryBean(){
        category = new Category();
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    private DataModel<Category> categoryModel = new ArrayDataModel<Category>(CategoryDAO.getAllCategory());
    
    private DataModel<Category> editCategoryModel = null;
    
    public DataModel<Category> getCategoryList(){
        return categoryModel;
    }
    
    public Category[] getCategoryParentList(){
        return CategoryDAO.getAllParentCategory();
    }
    
    public boolean isParentHasChild(int parentId){
        if(CategoryDAO.getAllChildCategory(parentId).length > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public Category[] getChildCategoryList(int parentId){
        return CategoryDAO.getAllChildCategory(parentId);
    }
    
    public Category getByCategory(int id){
        return CategoryDAO.getByCategory(id);
    }
    
    public DataModel<Category> getEditCategoryList(){
        editCategoryModel = new ArrayDataModel<Category>(CategoryDAO.getAllCustomCategory(category.getCategoryId()));
        return editCategoryModel;
    }
    
    public String create(){
        CategoryDAO.create(category);
        return "/category/list?faces-redirect=true";
    }
    
    public String edit(Category category){
        this.category = category;
        return "/category/edit";
    }
    
    public String update(){
        CategoryDAO.update(category);
        return "/category/list?faces-redirect=true";
    }
    
    public String delete(Category category){
        CategoryDAO.delete(category);
        return "/category/list?faces-redirect=true";
    }
    
}
