/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Discount;
import edu.nus.iss.pos.core.Transaction;

/**
 *
 * @author Zaid
 */
public interface IDiscountsService {
    float getDiscountForTransaction(Transaction transaction) throws Exception;
    
    // Calculate discount for transaction
    void applyDiscountForTransaction(Transaction transaction);
    
    public void addDiscount(Discount discount) throws Exception;
}
