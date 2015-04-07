package com.teamone.onlinemart.beans;

import com.teamone.onlinemart.dao.UserDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "loginBean")
@SessionScoped

/**
 *
 * @author javkhaa_j7
 */
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
 
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
    
    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/account/login.xhtml");
    }
 
    public String loginProject() {
        boolean result = UserDAO.login(uname, password);
        if(result) {
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
 
            return "/index?faces-redirect=true";
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("E-mail or password you entered is incorrect.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
            return "";
        }
    }
 
//    public String logout() {
//      HttpSession session = Util.getSession();
//      session.invalidate();
//      return "login";
//   }
}

