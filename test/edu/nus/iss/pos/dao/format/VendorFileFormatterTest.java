/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Vendor;
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
public class VendorFileFormatterTest {
    
    public VendorFileFormatterTest() {
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
     * Test of getInstance method, of class VendorFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        VendorFileFormatter result = VendorFileFormatter.getInstance();
        assertTrue(result instanceof VendorFileFormatter);
    }

    /**
     * Test of format method, of class VendorFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Vendor entity = new Vendor("testA", "testB");
        VendorFileFormatter instance = VendorFileFormatter.getInstance();
        String expResult = "testA,testB\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of readEntity method, of class VendorFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "testA,testB\n";
        VendorFileFormatter instance = VendorFileFormatter.getInstance();
        Vendor expResult = new Vendor("testA", "testB");
        Vendor result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class VendorFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "testA,testB\n";
        VendorFileFormatter instance = VendorFileFormatter.getInstance();
        String expResult = "testA";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
