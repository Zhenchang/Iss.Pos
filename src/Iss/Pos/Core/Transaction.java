/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iss.Pos.Core;

import java.util.Date;

/**
 *
 * @author Zaid
 */
public class Transaction {
    private int id;
    private String productId;
    private String memberId;
    private int quantityPurchased;
    private Date date;
    
    private Product product;
    private Member member;
    
     public Transaction(int id, String productId, String memberId, int quantityPurchased){
        setId(id);
        setQuantityPurchased(quantityPurchased);
        this.productId = productId;
        this.memberId = memberId;
    }
     
    public Transaction(int id, Product product, Member member, int quantityPurchased){
        this(id, product.getId(), member.getId(), quantityPurchased);
        setProduct(product);
        setMember(member);
    }
    
    private void setId(int id){
        if(id < 1) throw new IllegalArgumentException("id");
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        if(quantityPurchased < 1) throw new IllegalArgumentException("quantityPurchased");
        this.quantityPurchased = quantityPurchased;
    }

    public Date getDate() {
        return date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if(product == null) throw new IllegalArgumentException("product");
        this.productId = product.getId();
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if(member == null) throw new IllegalArgumentException("member");
        this.memberId = member.getId();
        this.member = member;
    }
    
    
    
    
}
