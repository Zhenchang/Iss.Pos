/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Date;

/**
 *
 * @author Zaid
 */
public class Discount implements IEntity {
    private String code;
    private String description;
    private Date startDate;
    private int discountPeriod; // -1: Always, 1..*: days.
    private int percentage;
    private boolean forMembers;

    public Discount(String code, String description, Date startDate, int discountPeriod, int percentage, boolean forMembers){
        setCode(code);
        setDescription(description);
        setStartDate(startDate);
        setDiscountPeriod(discountPeriod);
        setPercentage(percentage);
        setForMembers(forMembers);
    }
    
    @Override
    public String getKey() {
        return getCode();
    }

    public String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public final void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDiscountPeriod() {
        return discountPeriod;
    }

    public final void setDiscountPeriod(int discountPeriod) {
        this.discountPeriod = discountPeriod;
    }

    public int getPercentage() {
        return percentage;
    }

    public final void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isForMembers() {
        return forMembers;
    }

    public final void setForMembers(boolean forMembers) {
        this.forMembers = forMembers;
    }
    
    public boolean getForMembers(){
        return this.forMembers;
    }
    
}
