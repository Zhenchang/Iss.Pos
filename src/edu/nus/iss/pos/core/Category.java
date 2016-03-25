/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Zaid
 */
public class Category implements IEntity{
    
    private String id;
    private String name;
    
    private Collection<Product> products;
    private Collection<Vendor> vendors;
    
    public Category(){
        products = new HashSet<>();
        vendors = new HashSet<>();
    }
    
    public Category(String id, String name){
        this();
        setId(id);
        setName(name);
    }
    
    public Category(String id) {
        setId(id);
    }
    
    public Iterable<Product> getProducts(){
        return products;
    }
    public Iterable<Vendor> getVendors(){
        return vendors;
    }
    
    public void addProduct(Product p){
        products.add(p);
    }
    
    public void removeProduct(Product p){
        products.remove(p);
    }
    public void addVendor(Vendor v){
        vendors.add(v);
    }
    
    public void removeVendor(Vendor v){
        vendors.remove(v);
    }
    
    public final void setId(String id){
        if(id.length() != 3) throw new IllegalArgumentException("id");
        this.id = id.toUpperCase();
    }
    
    public final void setName(String name){
      if(name.length() < 3) throw new IllegalArgumentException("name");
      this.name = name;
    }
    
    public String getKey(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return getName();
    }
}
