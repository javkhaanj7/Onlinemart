package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.UserDAO;
import com.teamone.onlinemart.models.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author JChimidregzen
 */
@ManagedBean(name = "loginBean")
//@SessionScoped
@RequestScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
    private User user;
    private boolean loggedIn;
    
    
    public User getUser() {
        return Util.getUser();
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }
 
    public String loginProject() {
        boolean result = UserDAO.login(uname, password);
        if(result) {
            user = UserDAO.get(uname, password);
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
            session.setAttribute("user", user);
            loggedIn = true;
            if(user.getUserType().equalsIgnoreCase("admin") || user.getUserType().equalsIgnoreCase("vendor")) {
                return "/admin/control?faces-redirect=true";
            } else {
                return "/index?faces-redirect=true";
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("E-mail or password you entered is incorrect.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
            return "";
        }
    }
 
    public String logout() {
      loggedIn = false;
//      HttpSession session = Util.getSession();
//      session.invalidate();
      FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
      return "/index?faces-redirect=true";
   }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public String edit() {
        return "/account/edit";
    }
}

