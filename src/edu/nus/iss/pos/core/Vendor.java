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
public class Vendor implements IEntity {
    private String name;
    private String description;
    
    private final Collection<Category> categories;
    
    public Vendor(){
        categories = new HashSet<>();
    }
    
    public Vendor(String name, String description){
        this();
        this.name = name;
        this.description = description;
        
    }
    
    public Iterable<Category> getCategories(){
        return categories;
    }
    
    public void addCategory(Category category){
        categories.add(category);
    }
    
    public void removeCategory(Category category){
        categories.remove(category);
    }

    @Override
    public String getKey() {
        return name;
    }
    
    public String getDescription(){
        return this.description;
    }
}
