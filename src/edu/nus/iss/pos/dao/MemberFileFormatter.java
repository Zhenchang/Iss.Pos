/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Liu Zhenchang
 */
public class MemberFileFormatter implements IFileFormatter<Member>{

    @Override
    public String format(Member entity) {
        return entity.getKey() + "," + entity.getName() + "," + String.valueOf(entity.getLoyaltyPoints()) + "\n";
    }
    
}
