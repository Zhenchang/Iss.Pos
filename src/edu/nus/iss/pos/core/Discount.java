/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

/**
 *
 * @author Zaid
 */
public abstract class Discount implements IEntity {
    private String code;
    private String description;
    private int percentage;

    public Discount(String code, String description, int percentage){
        setCode(code);
        setDescription(description);
        setPercentage(percentage);

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

    public int getPercentage() {
        return percentage;
    }

    public final void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    
}
