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
    
    Member registerMember(String id, String name) throws Exception;
    
    Member searchMemberByName(String name) throws Exception;
    
    Member searchMemberById(String id) throws Exception;
    
    
}
