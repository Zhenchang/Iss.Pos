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
public abstract class AlwaysDiscount extends Discount {
    
    public AlwaysDiscount(String code, String description, int percentage){
        super(code, description,percentage);
        
    }
}
