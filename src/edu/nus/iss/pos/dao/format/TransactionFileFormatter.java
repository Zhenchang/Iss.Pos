/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Liu Zhenchang
 */
public class TransactionFileFormatter implements IFileFormatter<Transaction>{

    private static final TransactionFileFormatter singleton =  new TransactionFileFormatter();
    private static IUnitOfWork unitOfWork;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private TransactionFileFormatter(){
        
    }
    public static TransactionFileFormatter getInstance(IUnitOfWork unitOfWork){
        TransactionFileFormatter.unitOfWork = unitOfWork;
        return singleton;
    }
    
    @Override
    public String format(Transaction entity) {
        String str = "";
        for(TransactionDetail detail : entity.getTransactionDetails()){
            str += entity.getKey() + "," +  
                    detail.getProduct().getKey() + "," + 
                    entity.getCustomer().getKey() + "," + 
                    detail.getQuantityPurchased() + "," +
                    dateFormatter.format(entity.getDate()) + "\n";
        }
        return str;
    }
    @Override
    public Transaction readEntity(String data) throws Exception {
        String[] lines = data.split("\n");
        if(lines.length == 0) throw new Exception("Cannot map data to entity!");
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
                Customer c ;
                if(memberId.equals("PUBLIC")){
                    c = new Customer();
                }else{
                    c = (Member) unitOfWork.getRepository(RepoType.Member).getByKey(memberId);
                }
                result = new Transaction(id, purchasedDate, c);
            }
            if(result.getId() == id){
                Product p = (Product) unitOfWork.getRepository(RepoType.Product).getByKey(productId);
                TransactionDetail d = new TransactionDetail(result,p , quantity);
                result.addTransactionDetail(d);
            }
        }
        return result;
    }
    
    @Override
    public String getKey(String data) throws Exception{
        int pos = data.indexOf(",");
        if(pos < 1) throw new Exception("No key found!"); 
        String key = data.substring(0, pos);
        return key;
    }
    
}
