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
public class TransactionDetail {
    private int quantityPurchased;
    private Product product;
    private Transaction transaction;
    
    public TransactionDetail(Transaction transaction, Product product, int quantityPurchased){
        setQuantityPurchased(quantityPurchased);
        setProduct(product);
        setTransaction(transaction);
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public final void setQuantityPurchased(int quantityPurchased) {
        if(quantityPurchased < 1) throw new IllegalArgumentException("quantityPurchased");
        this.quantityPurchased = quantityPurchased;
    }

    public Product getProduct() {
        return product;
    }

    public final void setProduct(Product product) {
        if(product == null) throw new IllegalArgumentException("product");
        this.product = product;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public final void setTransaction(Transaction transaction) {
        if(transaction == null) throw new IllegalArgumentException("member");
        this.transaction = transaction;
    }
    
    public float subTotal(){
        return this.getQuantityPurchased() * this.getProduct().getPrice();
    }
}
