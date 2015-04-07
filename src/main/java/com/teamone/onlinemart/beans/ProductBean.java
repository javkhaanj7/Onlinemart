/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.sun.xml.internal.ws.api.PropertySet;
import com.teamone.onlinemart.dao.ProductDAO;
import com.teamone.onlinemart.models.Product;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

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
    private List<Product> products;// = new ArrayList<Product>();

//    public DataModel<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(DataModel<Product> products) {
//        this.products = products;
//    }
//    private DataModel<Product> products;// = new ArrayDataModel<>();
    public ProductBean() {
        product = new Product();
        products = new ArrayList<Product>();
//        products = new ArrayDataModel<>();
        itemIndex = 0;
        categoryBean = new CategoryBean();
    }

    private int itemIndex;

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public int getItemIndex() {
        return itemIndex;
    }
    
//    @ManagedProperty("#{add_new}")
//    public String add_new(){
//        
//    }
    
//    @ManagedProperty(name = "categoryBean", value = new CategoryBean())
    @ManagedProperty("#{categoryBean}")
    private CategoryBean categoryBean;

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    private void getList() {
        setProducts(ProductDAO.getAll());

    }

    //-------------------------------
    public String list() {
        getList();
        return "/product/list";
    }

    public String prepareCreate() {
        setProduct(new Product());
        return "Create";
    }

    public String create() throws IOException {
        System.out.println(product.getCategory_id());
        int generated_id = ProductDAO.save(product);
        if (generated_id != -1) {
            product.setId(generated_id);
            uploadFile();
            getList();            
            statusMsg = "Saved Successfully";
//            return "/product/list";
        } else {
            statusMsg = "Not success";
        }
        return null;

    }

    public String add() {
        product = new Product();
        return "/product/add?faces-redirect=true";
    }

    public String delete(Product p) {
        ProductDAO.delete(p.getId());
        getList();
        return "/product/list?faces-redirect=true";
    }

    public String edit(Product p) {
        this.product = p;
        return "/product/edit";
    }

    public String editUpdate() {
        ProductDAO.update(product);
        getList();
//        return "/product/list";
        return "list?faces-redirect=true";
    }

    private String statusMsg = "";

    public String getStatusMsg() {
        return statusMsg;
    }

    /*
     File upload
     */
    private static final long serialVersionUID = 9040359120893077422L;

    private Part part;
    private String statusMessage;

    public String uploadFile() throws IOException {

        // Extract file name from content-disposition header of file part
        String fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);

//		String basePath = "D:" + File.separator + "temp" + File.separator;
//		File outputFilePath = new File(basePath + fileName);
        String relativePath = "resources/img/";
        FacesContext facesContext = FacesContext.getCurrentInstance();

        File outputFilePath = new File(facesContext.getExternalContext().getRealPath(relativePath) + "\\" + product.getId() + "_image_" + fileName);
        System.out.println(facesContext.getExternalContext().getRealPath(relativePath) + "\\" + product.getId() + "_image_" + fileName);

        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = part.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            statusMessage = "File upload successfull !!";
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage = "File upload failed !!";
            ///Anhaar
            ProductDAO.delete(product.getId());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;    // return to same page
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
}
