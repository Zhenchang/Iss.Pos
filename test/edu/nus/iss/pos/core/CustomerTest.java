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
public class CustomerTest {
    Customer customer;
    
    public CustomerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        customer= new Customer();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setId method, of class Customer.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "R5454541152";
        customer.setId(id);
        String result = customer.getKey();
        assertEquals(result, id);
    }

    /**
     * Test of getKey method, of class Customer.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        String expResult = "R3442232332";
        customer.setId(expResult);
        String result;
        result = customer.getKey();
        assertEquals(expResult, result);
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
