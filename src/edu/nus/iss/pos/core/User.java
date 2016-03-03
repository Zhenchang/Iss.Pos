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

    @Override
    public String getKey() {
        return username;
    }
}
