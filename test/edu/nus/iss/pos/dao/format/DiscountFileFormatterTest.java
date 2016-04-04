/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Discount;
import edu.nus.iss.pos.core.FirstPurchaseDiscount;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zz
 */
public class DiscountFileFormatterTest {
    
    public DiscountFileFormatterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class DiscountFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        DiscountFileFormatter result = DiscountFileFormatter.getInstance();
        assertTrue(result instanceof DiscountFileFormatter);
    }

    /**
     * Test of format method, of class DiscountFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Discount entity = new FirstPurchaseDiscount("MEMBER_FIRST","First purchase by member",10);
        DiscountFileFormatter instance = DiscountFileFormatter.getInstance();
        String expResult = "MEMBER_FIRST,First purchase by member,ALWAYS,ALWAYS,10,M\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of readEntity method, of class DiscountFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "MEMBER_FIRST,First purchase by member,ALWAYS,ALWAYS,10,M\n";
        DiscountFileFormatter instance = DiscountFileFormatter.getInstance();
        Discount expResult = new FirstPurchaseDiscount("MEMBER_FIRST","First purchase by member",10);
        Discount result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class DiscountFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "MEMBER_FIRST,First purchase by member,ALWAYS,ALWAYS,10,M\n";
        DiscountFileFormatter instance = DiscountFileFormatter.getInstance();
        String expResult = "MEMBER_FIRST";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
