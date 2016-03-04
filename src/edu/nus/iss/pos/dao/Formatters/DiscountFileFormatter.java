/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.Formatters;

import edu.nus.iss.pos.core.Discount;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import java.util.Date;


/**
 *
 * @author zz
 */
public class DiscountFileFormatter implements IFileFormatter<Discount>{
    
    private DiscountFileFormatter(){}
    
    public static final DiscountFileFormatter singleton = new DiscountFileFormatter();
    
    public static DiscountFileFormatter getInstance(){
        return singleton;
    }


    @Override
    public String format(Discount entity) {
        return entity.getCode() + "," + 
                entity.getDiscountPeriod() + "," +
                entity.getStartDate() + "," + 
                entity.getDiscountPeriod() + "," + 
                entity.getPercentage() + "," +
                entity.getForMembers() + "\n";
    }

    @Override
    public Discount readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map the data to entity!");
        String []params = lines[0].split(",");
        if(params.length != 6) throw new Exception("Cannot map the data to entity!");
        String code = params[0];
        String description = params[1];
        Date startDate = new Date(params[2]);
        int discountPeriod = Integer.parseInt(params[3]); // -1: Always, 1..*: days.
        int percentage = Integer.parseInt(params[5]);
        boolean forMembers = Boolean.parseBoolean(params[6]);
        
        return new Discount(code, description, startDate, discountPeriod, percentage, forMembers);
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
}
