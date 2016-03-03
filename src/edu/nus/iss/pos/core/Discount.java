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
    private String id;
    private String code;
    private String description;
    private Date startDate;
    private int discountPeriod; // -1: Always, 1..*: days.
    private int percentage;
    private boolean isApplicable;

    @Override
    public String getKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
