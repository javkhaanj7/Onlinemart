/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

/**
 *
 * @author Rabi
 */
import com.teamone.onlinemart.models.Address;
import com.teamone.onlinemart.dao.VendorDAO;
import com.teamone.onlinemart.models.Vendor;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "vendorBean")
@SessionScoped
public class VendorBean implements Serializable {

    private Vendor vendor;

    public VendorBean() {
        vendor = new Vendor();
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String registerVendor() {
        VendorDAO dao = new VendorDAO();
        if (dao.register(vendor)) {
            return "/vendor/payment";
        } else {
            // System.out.println("Test==================");
            return "/vendor/registerVendor";
        }
    }

}
