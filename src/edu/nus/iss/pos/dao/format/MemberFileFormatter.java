/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Liu Zhenchang
 */
public class MemberFileFormatter implements IFileFormatter<Member>{

    private static final MemberFileFormatter singleton =  new MemberFileFormatter();
    private MemberFileFormatter(){
        
    }
    public static MemberFileFormatter getInstance(){
        return singleton;
    }
    
    @Override
    public String format(Member entity) {
        return entity.getKey() + "," + entity.getName() + "," + String.valueOf(entity.getLoyaltyPoints()) + "\n";
    }

    @Override
    public Member readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map data to entity!");
        String[] params = lines[0].split(",");
        if(params.length != 3) throw new Exception("Cannot map data to entity!");
        String id = params[0];
        String name = params[1];
        int loyaltyPoints = Integer.parseInt(params[2]);
        return new Member(id, name, loyaltyPoints);
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
    
}
