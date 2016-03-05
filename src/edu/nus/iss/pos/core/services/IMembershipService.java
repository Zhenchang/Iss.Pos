/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Member;

/**
 *
 * @author Vishnu
 */
public interface IMembershipService {
    
    void registerMember(String id, String name);
    
    Member searchMemberByName(String name);
    
    Member searchMemberById(String id);
    
    
}
