/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Vendor;
import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author zz
 */
public class VendorFileFormatter implements IFileFormatter<Vendor>{
    
    public static final VendorFileFormatter singleton = new VendorFileFormatter();
    
    private VendorFileFormatter(){}
    
    public static VendorFileFormatter getInstance(){
        return singleton;
    }

    @Override
    public String format(Vendor entity) {
        return entity.getKey() + "," + entity.getDescription() + "\n";
    }

    @Override
    public Vendor readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map the data to entity!");
        String[] params = lines[0].split(",");
        if(params.length != 2) throw new Exception("Cannot map the data to entity!");
        String name = params[0];
        String description = params[1];
        return new Vendor(name, description);
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
    
    
}
