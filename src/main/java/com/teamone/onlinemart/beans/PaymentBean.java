/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.OrderDAO;
import com.teamone.onlinemart.models.MyFinance;
import com.teamone.onlinemart.utils.Mail;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author javkhaa_j7
 */
@ManagedBean(name = "paymentBean")
@RequestScoped
public class PaymentBean {
    
    public PaymentBean(){
        myfinance = new MyFinance();
    }
    
    @ManagedProperty("#{cartBean}")
    private CartBean cartBean;
    
    private MyFinance myfinance;

    public CartBean getCartBean() {
        return cartBean;
    }

    public void setCartBean(CartBean cartBean) {
        this.cartBean = cartBean;
    }

    public MyFinance getMyfinance() {
        return myfinance;
    }

    public void setMyfinance(MyFinance myfinance) {
        this.myfinance = myfinance;
    }
    
    public String makeVendorPayment(){
        
        long userId = 0;
        String userName = "Michael";
        
        if(Util.getUser() != null)
            userId = Util.getUser().getId();
        
        if(Util.getUser() != null){
            userId = Util.getUser().getId();
            userName = Util.getUser().getFirstname();
        }
        OrderDAO.create(cartBean.getCartContent(), userId);
        
        String productList = "";
        
        for(int i = 0; i < cartBean.getCartContent().size(); i++){
            productList += cartBean.getCartContent().get(i).getProduct().getName() + " (" + cartBean.getCartContent().get(i).getQuantity() + ")<br/>";
        }
        
        String template = "Hello " + userName + ", <br/><br/>"
                + "<strong>Order information:</strong><br/>"
                + productList + "<br/>"
                + "<strong>Order total:</strong> $" + cartBean.totalAmount() + "<br/><br/>"
                + "<strong>Shipping address:</strong><br/>"
                + "1000 N 4th street, Fairfield, Iowa, 12345, United States";
        
        String template1 = "Hello " + userName + " <br/><br/>"
                + "<strong>Username requested order:</strong><br/>"
                + productList + "<br/>"
                + "<strong>Order total:</strong> $" + cartBean.totalAmount() + "<br/><br/>"
                + "<strong>Shipping address:</strong><br/>"
                + "1000 N 4th street, Fairfield, Iowa, 12345, United States";
        try {
            new Mail().sendEmail("onlinemart.shopping@gmail.com", "onlinemart.shopping@gmail.com", "Your Onlinemart order", template);
            new Mail().sendEmail("onlinemart.shopping@gmail.com", "onlinemart.shopping@gmail.com", "Customer order request", template1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "/index?faces-redirect=true";
    }
}
