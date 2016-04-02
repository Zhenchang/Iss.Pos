/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vishnu 
 */
public class Transaction implements IEntity {

    private int id;
    private Date date;
    private Customer customer;
    private List<TransactionDetail> transactionDetails;
    
    public Transaction(int id, Date date, Customer customer){
        setId(id);
        setDate(date);
        setCustomer(customer);
        transactionDetails = new ArrayList();
    }
    
    @Override
    public String getKey() {
        return String.valueOf(id);
    }
    
    private void setId(int id){
        if(id < 1) throw new IllegalArgumentException("id");
        this.id = id;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }
   
    public Customer getCustomer() {
        return customer;
    }
    
    public Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final void setCustomer(Customer customer) {
        if(customer == null) throw new IllegalArgumentException("member");
        this.customer = customer;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void addTransactionDetail(TransactionDetail transactionDetail){
        if(transactionDetail == null) throw new IllegalArgumentException("transactionDetail");
        if(!transactionDetail.getTransaction().equals(this)) throw new IllegalArgumentException("transactionDetail");
        transactionDetails.add(transactionDetail);
    }
    
    public void removeTransactionDetail(TransactionDetail transactionDetail){
        if(transactionDetail == null) throw new IllegalArgumentException("transactionDetail"); 
        transactionDetails.remove(transactionDetail);
    }
    
    public float getTotalWithoutDiscount(){
        float sum = 0;
        Iterable<TransactionDetail> transactionDetails =  this.getTransactionDetails();
        for(TransactionDetail detail : transactionDetails){
            sum += detail.getProduct().getPrice();
        }
        return sum;
    }
}
