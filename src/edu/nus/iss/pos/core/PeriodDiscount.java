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
public class PeriodDiscount extends Discount {
    private Date startDate;
    private int discountPeriod;
    private boolean forMembers;
    public PeriodDiscount(String code, String description, float percentage,Date startDate, int discountPeriod, boolean forMembers){
        super(code, description,percentage);
        setStartDate(startDate);
        setDiscountPeriod(discountPeriod);
        setForMembers(forMembers);
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
