/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.Formatters;

import edu.nus.iss.pos.core.IEntity;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Liu Zhenchang
 */
public class FileFormatterFactory {
    public IFileFormatter getFormatter(FileType type) throws Exception{
        switch(type){
            case User: return new MemberFileFormatter();
            case Member: return new MemberFileFormatter();
            case Category: return new MemberFileFormatter();
            case Product: return new MemberFileFormatter();
            case Transaction: return new TransactionFileFormatter();
            case Discount: return new MemberFileFormatter();
            case Vendor: return new MemberFileFormatter();
            default: throw new Exception("No Formatter Found!");
        }
    }
}

