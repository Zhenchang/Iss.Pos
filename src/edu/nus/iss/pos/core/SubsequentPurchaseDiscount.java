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
public class SubsequentPurchaseDiscount extends AlwaysDiscount {

    public SubsequentPurchaseDiscount(String code, String description, int percentage) {
        super(code, description, percentage);
    }
    
}
