/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;

/**
 *
 * @author Liu Zhenchang
 */
public interface ISalesService {
    
    Transaction beginTransaction(Member member);
    TransactionDetail addToCart(Transaction transaction, Product product, int quantity);
    void checkout(Transaction transaction);
    
    
}
