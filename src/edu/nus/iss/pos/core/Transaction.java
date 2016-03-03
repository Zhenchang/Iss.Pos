/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Vishnu 
 */
public class Transaction implements IEntity {

    private int id;
    private Date date;
    private Member member;
    private String memberId;
    private Collection<TransactionDetail> transactionDetails;
    
    public Transaction(int id, Date date, Member member){
        setId(id);
        setDate(date);
        setMember(member);
    }
    
    public Transaction(int id, Date date, String memberId){
        setId(id);
        setDate(date);
        setMemberId(memberId);
    }
    
    @Override
    public String getKey() {
        return String.valueOf(id);
    }
    
    private void setId(int id){
        if(id < 1) throw new IllegalArgumentException("id");
        this.id = id;
    }

    public Iterable<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }
   
    public Member getMember() {
        return member;
    }
    
    public String getMemberId() {
        return memberId;
    }

    public Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final void setMember(Member member) {
        if(member == null) throw new IllegalArgumentException("member");
        this.memberId = member.getKey();
        this.member = member;
    }
    
    public final void setMemberId(String memberId){
        this.memberId = memberId;
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
}
