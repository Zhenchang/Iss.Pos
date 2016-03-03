/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

/**
 *
 * @author Zaid
 * @author Liu Zhenchang
 */
public class User implements IEntity {
    private String username;
    private String password;

    public User(String username, String password){
        setUsername(username);
        setPassword(password);
    }
    
    @Override
    public String getKey() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }
    
    public boolean verify(String username, String password){
        return getUsername().equals(username) && getPassword().equals(password);
    }
}
