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
    
    public Customer(){
        setId("PUBLIC");
    }
    
    protected void setId(String id){
        if(id.length() < 3) throw new IllegalArgumentException("id");
        this.id = id;
    }
    
      
    public String getKey(){
        return this.id;
    }
}
