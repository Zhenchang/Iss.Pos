/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.dao.IFileFormatter;
import edu.nus.iss.pos.core.*;

/**
 *
 * @author zz
 */
public class CategoryFileFormatter implements IFileFormatter<Category>{
    
    public static final CategoryFileFormatter singleton = new CategoryFileFormatter();
    
    
    private CategoryFileFormatter(){
    
    }
    public static CategoryFileFormatter getInstance(){
        return singleton;
    }

    @Override
    public Category readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map data to entity!");
        String[] params = lines[0].split(",");
        if(params.length != 0) throw new Exception("Cannot map data to entity!");
        String categoryId = params[0];
        String name = params[1];
        return new Category(categoryId, name);
    }

    @Override
    public String format(Category entity) {
           return entity.getKey() + "," + entity.getName() + "\n";
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }

}
