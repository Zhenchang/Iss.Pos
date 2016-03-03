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
    private String productId;
    private int quantityPurchased;
    private Product product;
    
    private String transactionId;
    private Transaction transaction;
    
    public TransactionDetail(Transaction transaction, Product product, int quantityPurchased){
        setQuantityPurchased(quantityPurchased);
        setProduct(product);
        setTransaction(transaction);
    }
    
    public TransactionDetail(Transaction transaction, String productId, int quantityPurchased){
        setQuantityPurchased(quantityPurchased);
        setProductId(productId);
        setTransaction(transaction);
    }

    public String getProductId() {
        return productId;
    }

    public String getTransactionId() {
        return transactionId;
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
        this.setProductId(product.getKey());
        this.product = product;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public final void setTransaction(Transaction transaction) {
        if(transaction == null) throw new IllegalArgumentException("member");
        this.transactionId = transaction.getKey();
        this.transaction = transaction;
    }

    public final void setProductId(String productId) {
        this.productId = productId;
    }
    
}
