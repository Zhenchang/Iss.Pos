/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.AlwaysDiscount;
import edu.nus.iss.pos.core.Discount;
import edu.nus.iss.pos.core.FirstPurchaseDiscount;
import edu.nus.iss.pos.core.PeriodDiscount;
import edu.nus.iss.pos.core.SubsequentPurchaseDiscount;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author zz
 */
public class DiscountFileFormatter implements IFileFormatter<Discount>{
    
    private DiscountFileFormatter(){}
    
    private static final DiscountFileFormatter singleton = new DiscountFileFormatter();
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public static DiscountFileFormatter getInstance(){
        return singleton;
    }


    @Override
    public String format(Discount entity) {
        if(entity instanceof AlwaysDiscount){
            return entity.getCode() + "," +
                    entity.getDescription() + "," +
                    "ALWAYS" + 
                    "ALWAYS" + 
                    entity.getPercentage() + "," +
                    "M";
        }
        PeriodDiscount exactEntity = (PeriodDiscount) entity;
        return entity.getCode() + "," +
                entity.getDescription() + "," +
                dateFormatter.format(exactEntity.getStartDate()) + "," + 
                exactEntity.getDiscountPeriod() + "," + 
                entity.getPercentage() + "," +
                (exactEntity.getForMembers() ? "M" : "A");
        
     }

    @Override
    public Discount readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map the data to entity!");
        String []params = lines[0].split(",");
        if(params.length != 6) throw new Exception("Cannot map the data to entity!");
        String code = params[0];
        String description = params[1];
        float percentage = Float.parseFloat(params[5]);
        
        if(code.equals("MEMBER_FIEST")){
            return new FirstPurchaseDiscount(code, description, percentage);
        }else if(code.equals("MEMBER_SUBSEQ")){
            return new SubsequentPurchaseDiscount(code, description, percentage);
        }else{
            Date startDate = dateFormatter.parse(params[2]);
            int discountPeriod = Integer.parseInt(params[3]);
            boolean forMembers = Boolean.parseBoolean(params[6]);
            return new PeriodDiscount(code, description, percentage, startDate, discountPeriod, forMembers);
        }
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
}
