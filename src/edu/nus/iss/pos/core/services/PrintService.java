/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;

/**
 *
 * @author Zaid
 */
public class PrintService {
    
    public static String printTransaction(Transaction transaction, float total, int discount, int pointsEarned, int pointsConsumed){
        String str = "";
        str += marginSpaceBefore("University Souvenir Store" , 15) + System.lineSeparator();
        str += marginSpaceBefore("Invoice" , 12) + System.lineSeparator();
        str += transaction.getDate().toString() + System.lineSeparator();
        str += "-------------------------------"+ System.lineSeparator();
        for(TransactionDetail d : transaction.getTransactionDetails()){
            str += marginSpaceAfter(d.getProduct().getName(), 17) + marginSpaceAfter(d.getQuantityPurchased() + "", 5) + marginSpaceBefore( String.format("$ %.2f", d.subTotal()), 8) + System.lineSeparator();
        }
        str += "-------------------------------" + System.lineSeparator();
        str += marginSpaceAfter("Sub Total", 17) + marginSpaceBefore(String.format("$ %.2f", transaction.getTotalWithoutDiscount()), 13) + System.lineSeparator();
        str += marginSpaceAfter("Discount", 17) + marginSpaceBefore(String.format("%d%%", discount), 13) + System.lineSeparator();
        if(transaction.getCustomer() instanceof Member){
            str += marginSpaceAfter("Redeemed Points", 17) + marginSpaceBefore(String.format("%d", pointsConsumed), 13) + System.lineSeparator();
            str += marginSpaceAfter("Points Value", 17) + marginSpaceBefore(String.format("$ %.2f", Member.convertPointsToDollars(pointsConsumed)), 13) + System.lineSeparator();
        }
        str += "-------------------------------" + System.lineSeparator();
        str += marginSpaceAfter("TOTAL", 17) + marginSpaceBefore(String.format("$ %.2f", total), 13) + System.lineSeparator();
        str += "-------------------------------" + System.lineSeparator();
        if(transaction.getCustomer() instanceof Member){
            str += marginSpaceAfter("Points Earned", 17) + marginSpaceBefore(String.format("%d", pointsEarned), 13) + System.lineSeparator();
        }
        return str;
    }
    private static String marginSpaceAfter(String s, int margin){
        String str = s;
        if(margin > s.length()){
            for(int i=0;i<margin-s.length();i++){
                str += " ";
            }
        }
        return str;
    }
    
    private static String marginSpaceBefore(String s, int margin){
        String str = s;
        if(margin > s.length()){
            for(int i=0;i<margin-s.length();i++){
                str = " " + str;
            }
        }
        return str;
    }
    
}
