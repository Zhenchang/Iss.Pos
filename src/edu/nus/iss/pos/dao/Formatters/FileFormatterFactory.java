/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.Formatters;

import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Liu Zhenchang
 */
public class FileFormatterFactory {
    public IFileFormatter getFormatter(FileType type) throws Exception{
        switch(type){
            case User: return UserFileFormatter.getInstance();
            case Member: return MemberFileFormatter.getInstance();
            case Category: throw new Exception("No Formatter Found!");
            case Product: throw new Exception("No Formatter Found!");
            case Transaction: return TransactionFileFormatter.getInstance();
            case Discount: throw new Exception("No Formatter Found!");
            case Vendor: throw new Exception("No Formatter Found!");
            default: throw new Exception("No Formatter Found!");
        }
    }
}