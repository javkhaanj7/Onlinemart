/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teamone.onlinemart.filters;

import com.teamone.onlinemart.models.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author javkhlant
 */
@WebFilter("/product/*")
public class ProductFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    // /product/details.xhtml
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if(req.getServletPath().startsWith("/product/details.xhtml")) {
            chain.doFilter(req, res);
        } else if(session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/account/login.xhtml");
        } else {
            User user = (User) session.getAttribute("user");
            if(user != null && (user.getUserType().equalsIgnoreCase("admin") || user.getUserType().equalsIgnoreCase("vendor"))) {
                chain.doFilter(req, res);
            } else {
                res.sendRedirect(req.getContextPath() + "/account/login.xhtml");
            }
        }
    }

    @Override
    public void destroy() {}
}
