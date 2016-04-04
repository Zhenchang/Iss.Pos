/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ankan
 */
public class MemberTest {
    private String id="E336766767";
    private String name="Anakan";
    private int loyaltyPoints= 0;
    Member member;
    
    
    public MemberTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
      member = new Member(id,name,loyaltyPoints);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addLoyaltyPoints method, of class Member.
     */
    @Test
    public void testAddLoyaltyPoints() {
        System.out.println("addLoyaltyPoints");
        float salePrice;
        salePrice = 500.0F;
        int expResult = 50;
        int result = member.addLoyaltyPoints(salePrice);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of redeemPoints method, of class Member.
     */
    @Test
    public void testRedeemPoints() throws Exception {
        System.out.println("redeemPoints");
        float salePrice = 300.0F;
        float expResult = 300.0F;
        float result = member.redeemPoints(salePrice, 0, false);
        assertEquals(expResult, result,0.0F);
        int loyaltyPointsNeg = 100;
        Member instance = new Member(id, name, loyaltyPointsNeg);
        float expResultNeg = 300.0F;
        float resultNeg = instance.redeemPoints(salePrice, 0, false);
        assertNotSame(expResultNeg, resultNeg);
        
       
    }

    /**
     * Test of convertPointsToDollars method, of class Member.
     */
    @Test
    public void testConvertPointsToDollars() {
        System.out.println("convertPointsToDollars");
        int points = 20;
        float expResult = 1.0f;
        float result = member.convertPointsToDollars(points);
        assertEquals(expResult, result, 0.0f);  
    }

    /**
     * Test of convertDollarsToPoints method, of class Member.
     */
    @Test
    public void testConvertDollarsToPoints() {
        System.out.println("convertDollarsToPoints");
        float dollars = 550F;
        int expResult = 55;
        int result = member.convertDollarsToPoints(dollars);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of checkPoints method, of class Member.
     */
    @Test
    public void testCheckPoints() {
        System.out.println("checkPoints");
        int points = 10;
        int loyaltyPoints = 100;
        Member instance;
        instance = new Member(id, name, loyaltyPoints);
        boolean expResult = true;
        boolean result = instance.checkPoints(points);
        assertEquals(expResult, result);       
    }

    /**
     * Test of setName method, of class Member.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Zaid";
        member.setName(name);
        String result = member.getName();
        assertEquals(name, result);     
    }
    /**
     * Test of getName method, of class Member.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String name = "Liu";
        member.setName(name);
        String result = member.getName();
        assertEquals(name, result);
        
    }

    /**
     * Test of getLoyaltyPoints method, of class Member.
     */
    @Test
    public void testGetLoyaltyPoints() {
        System.out.println("getLoyaltyPoints");
        int expResult = 0;
        int result = member.getLoyaltyPoints();
        assertEquals(expResult, result);      
    }    
}
