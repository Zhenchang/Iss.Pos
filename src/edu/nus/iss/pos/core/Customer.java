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
public class Customer implements IEntity  {
    private String id;
    private final static Customer instance = new Customer();
    
    protected Customer(){
        setId("PUBLIC");
    }
    
    public static Customer getInstance(){
        return instance;
    }
    
    protected void setId(String id){
        if(id.length() < 3) throw new IllegalArgumentException("id");
        this.id = id;
    }
    
      
    public String getKey(){
        return this.id;
    }
}
