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
public class Member extends Customer{
    private String name;
    private int loyaltyPoints;
    
    private static float DOLLAR_TO_POINT = 10;
    private static float POINT_TO_DOLLAR = 100;
    private static int   DOLLAR_PER_POINTS = 5;
    
    
    public Member(String id, String name){
        setId(id);
        setName(name);
        setLoyaltyPoints(-1);
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
    
    public float redeemPoints(float salePrice, int points, boolean removePoints) throws Exception{
        float finalPrice = salePrice;
        
        float pointsValue = convertPointsToDollars(points);
        
        finalPrice = salePrice - pointsValue;
        if(removePoints) removeLoyaltyPoints(points);
        return finalPrice;
    }
    
    public static float convertPointsToDollars(int points){
        return DOLLAR_PER_POINTS * (points / POINT_TO_DOLLAR) ;
    }
    public static int convertDollarsToPoints(float dollars){
        return (int) Math.floor(dollars / DOLLAR_TO_POINT);
    }
    
    public boolean checkPoints(int points){
        return (points <= loyaltyPoints);
    }
    
  
    public void setName(String name){
        if(name.length() < 2) throw new IllegalArgumentException("name");
        this.name = name;
    }
    
    private void setLoyaltyPoints(int loyaltyPoints){
        if(loyaltyPoints > -2) this.loyaltyPoints = loyaltyPoints;
        else throw new IllegalArgumentException("loyaltyPoints");
    }
    
    public String getName(){
        return this.name;
    }
    public int getLoyaltyPoints(){
        return this.loyaltyPoints;
    }

}
