/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.ProductDAO;
import com.teamone.onlinemart.models.Product;
import com.teamone.onlinemart.models.User;
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
import javax.faces.context.FacesContext;
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

    private List<Product> products;// = new ArrayList<Product>();
    
    
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

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
        products = new ArrayList<>();
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
        statusMessage = "";
        statusMsg = "";
        return "Create";
    }

    public String create() throws IOException {  
        User u = (User)Util.getUser();
        if(u == null){
            return "/index?faces-redirect=true";
        }
        
        product.setVendor_id((int) u.getId());
        int generated_id = ProductDAO.save(product);
        if (generated_id != -1) {
            product.setId(generated_id);
            uploadFile();
            getList();            
            statusMsg = "Saved Successfully";
            return "/product/list?faces-redirect=true";
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
        return "list?faces-redirect=true";
    }

    private String statusMsg = "";

    public String getStatusMsg() {
        return statusMsg;
    }

    /*
     File upload
     */

    private Part part;
    private String statusMessage;

    public String uploadFile() throws IOException {

        String fileName = getFileName(part);        
        
        String relativePath = "resources/img/";
        String path;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        path = ((ServletContext)facesContext.getExternalContext().getContext()).getRealPath(relativePath);
                
        File outputFilePath = new File(path + "\\"+product.getVendor_id() + "\\" + product.getId() + "_image");

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
            product.setImagePath("/resources/img/" +product.getVendor_id()+"/"+product.getId()+"_image");
        } catch (IOException e) {
            statusMessage = "File upload failed !!";
            statusMsg = "Not success";
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
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
    
    /*
    Pagination
    */
//    private PaginationHelper pagination;
//    
//    public PaginationHelper getPagination() {
//        if (pagination == null) {
//            pagination = new PaginationHelper(10) {
//
//                @Override
//                public int getItemsCount() {
//                    return ProductDAO.getAll().size();
//                }
//
//                @Override
//                public DataModel createPageDataModel() {
//                    List list = ProductDAO.getAll();                                        
//                    return new ListDataModel(ProductDAO.findRange(getPageFirstItem(), getPageFirstItem() + getPageSize()));
//                }
//            };
//        }
//        return pagination;
//    }
//    private DataModel items = null;
    
    public Product[] getTopSelledList(){
        return ProductDAO.findTop(8);
    }
    
    public Product[] getNewProductList(){
        return ProductDAO.findNew(8);
    }
    
}
