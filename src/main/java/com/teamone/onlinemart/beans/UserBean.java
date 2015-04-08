/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.UserDAO;
import com.teamone.onlinemart.models.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JChimidregzen
 */
@ManagedBean(name = "userBean")
//@SessionScoped
@RequestScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private User user;
    private boolean edit;

    public UserBean() {
        user = new User();
    }
    
    public String register(){
        if(UserDAO.exists(user.getEmail())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("This email is already in use.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
            return "";
        } else {
            user.setUserType("customer");
            int count = UserDAO.create(user);
            if(count > 0) {
                return "/account/login?faces-redirect=true";
            } else {
                return "unsuccess?faces-redirect=true";
            }
        }
    }
    
    public String update() {
        if(UserDAO.exists(user.getEmail(), user.getId())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("This email is already in use.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
            return "";
        } else {
            int count = UserDAO.update(user);
            if(count > 0) {
                HttpSession session = Util.getSession();
                session.setAttribute("username", user.getEmail());
                session.setAttribute("user", user);
                return "/account/profile?faces-redirect=true";
            } else {
                return "unsuccess?faces-redirect=true";
            }
        }
    }
    
    public void validatePassword(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        
        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();
        
        // Let required="true" do its job
        if(password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        
        if(!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Password must match confirm password.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();
        }
    }

    /**
     * @return the edit
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * @param edit the edit to set
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
