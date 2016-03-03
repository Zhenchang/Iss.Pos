/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.Formatters;

import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Liu Zhenchang
 */
public class TransactionFileFormatter implements IFileFormatter<Transaction>{

    @Override
    public String format(Transaction entity) {
        String str = "";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for(TransactionDetail detail : entity.getTransactionDetails()){
            str += entity.getKey() + "," +  
                    detail.getProductId() + "," + 
                    entity.getMemberId() + "," + 
                    detail.getQuantityPurchased() + "," +
                    dateFormatter.format(entity.getDate()) + "\n";
        }
        return str;
    }
    @Override
    public Transaction readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map data to entity!");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Transaction result = null;
        for(String line : lines){
            String[] params = line.split(",");
            if(params.length != 5) throw new Exception("Cannot map data to entity!");
            int id = Integer.parseInt(params[0]);
            String productId = params[1];
            int quantity = Integer.parseInt(params[3]);
            if(result == null){
                String memberId = params[2];
                Date purchasedDate = dateFormatter.parse(params[4]);
                result = new Transaction(id, purchasedDate, memberId);
            }
            if(result.getId() == id){
                TransactionDetail d = new TransactionDetail(result, productId, quantity);
                result.addTransactionDetail(d);
            }
        }
        return result;
    }
    
}
