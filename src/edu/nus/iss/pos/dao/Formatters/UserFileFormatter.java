/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.formatters;

import edu.nus.iss.pos.core.User;
import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Zaid
 */
public class UserFileFormatter implements IFileFormatter<User> {
    
    private static final UserFileFormatter singleton =  new UserFileFormatter();
    private UserFileFormatter(){
        
    }
    public static UserFileFormatter getInstance(){
        return singleton;
    }
    
    @Override
    public String format(User entity) {
        return entity.getUsername() + "," + entity.getPassword() + "\n";
    }
    
    @Override
    public User readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map data to entity!");
        String[] params = lines[0].split(",");
        if(params.length != 2) throw new Exception("Cannot map data to entity!");
        String username = params[0];
        String password = params[1];
        return new User(username, password);
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
}
