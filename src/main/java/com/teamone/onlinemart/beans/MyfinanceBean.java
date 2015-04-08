/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.MyfinanceDAO;
import com.teamone.onlinemart.models.MyFinance;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Rabi
 */
@ManagedBean(name = "myfinanceBean")
@SessionScoped
public class MyfinanceBean implements Serializable {

    private MyFinance myfinance;

    public MyfinanceBean() {
        myfinance = new MyFinance();
    }

    /**
     * @return the myfinance
     */
    public MyFinance getMyfinance() {
        return myfinance;
    }

    /**
     * @param myfinance the myfinance to set
     */
    public void setMyfinance(MyFinance myfinance) {
        this.myfinance = myfinance;
    }

    public String makeVendorPayment() {
        if (MyfinanceDAO.makePayment(myfinance)) {
            return "/vendor/register-success";
        } else {
            return "/vendor/payment";
    
        }
    }
}
