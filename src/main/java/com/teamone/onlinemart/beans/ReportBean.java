/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.beans;

import com.google.gson.Gson;
import com.teamone.onlinemart.dao.ReportDAO;
import com.teamone.onlinemart.models.ReportMainModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

/**
 *
 * @author Ichko
 */
@ManagedBean
@RequestScoped
public class ReportBean {

    public String main() {

        return "/report/main?faces-redirect=true";
    }

    public ReportBean() {
        list= new ArrayDataModel<>();
        months = "[]";
        amounts = "[]";
        quantities = "[]";
        startDate = "Start Date";
        endDate = "End Date";
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        startDate = String.valueOf(year) + "-" + String.valueOf(month);
        //Assign report value
    }
    Gson parser = new Gson();

    public String search() {
        mainModel = ReportDAO.getMain("6", startDate, endDate);
        for (ReportMainModel t : mainModel) {
            monthList.add(t.getDate());
            quantityList.add(t.getTotal_quantity());
            amountList.add(t.getTotal_amount());
        }
        months = parser.toJson(monthList);
        quantities = parser.toJson(quantityList);
        amounts = parser.toJson(amountList);
        ReportMainModel[] r = new ReportMainModel[mainModel.size()];
        r = mainModel.toArray(r);
        list = new ArrayDataModel<>(r);
        return null;
    }

    private String vendor_id = Util.getUserId();

    private Type type;

    private List<String> monthList = new ArrayList<>();
    private List<Long> quantityList = new ArrayList<>();
    private List<Double> amountList = new ArrayList<>();

    private String months;
    private String quantities;
    private String amounts;

    private List<ReportMainModel> mainModel;    
    private DataModel<ReportMainModel> list;

    public DataModel<ReportMainModel> getMainList() {
        return list;
    }

    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }
}
