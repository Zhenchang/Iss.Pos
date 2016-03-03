/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao;

import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import java.text.SimpleDateFormat;

/**
 *
 * @author Liu Zhenchang
 */
public class TransactionFileFormatter implements IFileFormatter<Transaction>{

    @Override
    public String format(Transaction entity) {
        String str = "";
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        for(TransactionDetail detail : entity.getTransactionDetails()){
            str += entity.getKey() + "," +  
                    detail.getProductId() + "," + 
                    entity.getMemberId() + "," + 
                    detail.getQuantityPurchased() + "," +
                    dateFormater.format(entity.getDate()) + "\n";
        }
        return str;
    }
    
}
