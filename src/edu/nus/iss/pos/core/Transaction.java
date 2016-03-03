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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMember(Member member) {
        if(member == null) throw new IllegalArgumentException("member");
        this.memberId = member.getKey();
        this.member = member;
    }
    
    
}
