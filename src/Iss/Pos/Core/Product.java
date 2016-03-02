/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iss.Pos.Core;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Zaid
 */
public class Product {
    private final String id;
    private String name;
    private String description;
    private int quantity;
    private float price;
    private int barcodeNumber;
    private int reorderQuantity;
    private int orderQuantity; 
    
    private final Category category;
    private final Collection<Transaction> transactions;
    
    public Product(Category c, int index){
        category = c;
        this.id = c.getId() + "/" + String.valueOf(index);
        this.transactions = new HashSet<>();
    }
    
    public void addTransaction(Transaction t){
        this.transactions.add(t);
    }

    public void removeTransaction(Transaction t){
        this.transactions.remove(t);
    }
    
    public Iterable<Transaction> getTransactions(){
        return transactions;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setBarcodeNumber(int barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public int getBarcodeNumber() {
        return barcodeNumber;
    }

    public int getReorderQuantity() {
        return reorderQuantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public Category getCategory() {
        return category;
    }
}
