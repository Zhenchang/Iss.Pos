/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

/**
 *
 * @author Zaid
 */
public class Member implements IEntity {
    private String id;
    private String name;
    private int loyaltyPoints;
    
    private final float DOLLAR_TO_POINT = 10;
    private final float POINT_TO_DOLLAR = 100;
    private final int   DOLLAR_PER_POINTS = 5;
    
    
    public Member(String id, String name){
        setId(id);
        setName(name);
        setLoyaltyPoints(0);
    }
    
    public Member(String id, String name, int loyaltyPoints){
        this(id, name);
        setLoyaltyPoints(loyaltyPoints);
    }
    
    public int addLoyaltyPoints(float salePrice){
        int points = convertDollarsToPoints(salePrice);
        loyaltyPoints += points;
        return loyaltyPoints;
    }
    
    private int removeLoyaltyPoints(int points) throws Exception{
        if(!checkPoints(points)) throw new Exception("Not enough points");
        loyaltyPoints -= points;
        return loyaltyPoints;
    }
    
    public float redeemPoints(float salePrice) throws Exception{
        float finalPrice = salePrice;
        if(salePrice < DOLLAR_PER_POINTS) return salePrice;
        
        float pointsValue = convertPointsToDollars(this.loyaltyPoints);
        if(pointsValue < salePrice){
            finalPrice = salePrice - pointsValue;
            int points =(int) Math.floor((pointsValue / DOLLAR_PER_POINTS)*DOLLAR_TO_POINT);
            removeLoyaltyPoints(points);
        }else{
            int pointsNeeded = (int) (Math.floor(salePrice / DOLLAR_PER_POINTS) * POINT_TO_DOLLAR);
            finalPrice = salePrice - convertPointsToDollars(pointsNeeded);
            removeLoyaltyPoints(pointsNeeded);
        }
        return finalPrice;
    }
    
    public int convertPointsToDollars(int points){
        return DOLLAR_PER_POINTS * (int) Math.floor(points / POINT_TO_DOLLAR) ;
    }
    public int convertDollarsToPoints(float dollars){
        return (int) Math.floor(dollars / DOLLAR_TO_POINT);
    }
    
    public boolean checkPoints(int points){
        return (points < loyaltyPoints);
    }
    
    private void setId(String id){
        if(id.length() < 3) throw new IllegalArgumentException("id");
        this.id = id;
    }
    
    private void setName(String name){
        if(name.length() < 2) throw new IllegalArgumentException("name");
        this.name = name;
    }
    
    private void setLoyaltyPoints(int loyaltyPoints){
        if(loyaltyPoints == -1) this.loyaltyPoints = 0;
        else if(loyaltyPoints > -1) this.loyaltyPoints = loyaltyPoints;
        else throw new IllegalArgumentException("loyaltyPoints");
    }
    
    public String getName(){
        return this.name;
    }
    public int getLoyaltyPoints(){
        return this.loyaltyPoints;
    }
    
    public String getKey(){
        return this.id;
    }
}
