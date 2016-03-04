/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Zaid
 * @author Vishnu
 * 
 */
public class Product  implements IEntity {
    private String id;
    private String name;
    private String description;
    private int availableQuantity;
    private float price;
    private String barcodeNumber;
    private int reorderQuantity;
    private int orderQuantity; 
    
    private Category category;
    private Collection<Transaction> transactions;
    
    // id = CLO/1
    public Product(Category c, int index){
        category = c;
        this.id = c.getKey() + "/" + String.valueOf(index);
        this.transactions = new HashSet<>();
    }

    public Product(String id, String name, String description, int availableQuantity, float price, String barcodeNumber, int reorderQuantity, int orderQuantity) {
        this.setId(id);
        this.setName(name);;
        this.setDescription(description);
        this.setQuantity(orderQuantity);;
        this.setPrice(price);
        this.setDescription(description);
        this.setReorderQuantity(reorderQuantity);
        this.setOrderQuantity(orderQuantity);
    }
    
    public String getCategoryId(){
        return this.id.split("/")[0];
    }
    
    private void setId(String id){
        this.id = id;
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

    public final void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.availableQuantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public final void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getKey() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return availableQuantity;
    }

    public float getPrice() {
        return price;
    }

    public String getBarcodeNumber() {
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
