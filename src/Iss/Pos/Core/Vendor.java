/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iss.Pos.Core;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Zaid
 */
public class Vendor {
    private String name;
    private String description;
    
    private final Collection<Category> categories;
    
    public Vendor(){
        categories = new HashSet<>();
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
}
