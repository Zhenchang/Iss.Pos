/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.dao.IFileFormatter;

/**
 *
 * @author Liu Zhenchang
 */
public class FileFormatterFactory {
    public static IFileFormatter getFormatter(FileType type) throws Exception{
        switch(type){
            case User: return UserFileFormatter.getInstance();
            case Member: return MemberFileFormatter.getInstance();
            case Category: return CategoryFileFormatter.getInstance();
            case Product: return ProductFileFormatter.getInstance();
            case Transaction: return TransactionFileFormatter.getInstance();
            case Discount: return DiscountFileFormatter.getInstance();
            case Vendor: return VendorFileFormatter.getInstance();
            default: throw new Exception("No Formatter Found!");
        }
    }
}