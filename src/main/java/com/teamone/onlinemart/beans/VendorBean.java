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
import com.teamone.onlinemart.dao.VendorDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "vendorBean")
@SessionScoped
public class VendorBean implements Serializable {

    private String userType;
    private String firstname;
    private String lastname;
    private String email;
    private String vendorName;
    private String password;
    private String addressId;

    private Address ad;

    public VendorBean() {
        ad = new Address();
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String registerVendor() {
        VendorDAO dao = new VendorDAO();
        if (dao.register(this)) {
            return "register-success";
        } else {
            return "registerVendor";
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + lastname + " " + firstname;
    }

    /**
     * @return the ad
     */
    public Address getAd() {
        return ad;
    }

    /**
     * @param ad the ad to set
     */
    public void setAd(Address ad) {
        this.ad = ad;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * @param addressId the addressId to set
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

}
