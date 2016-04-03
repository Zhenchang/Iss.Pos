/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zaid
 */
public class VendorTest {
    
    private Vendor vendor;
    private String name;
    private String description;
    private Category category;
    
    public VendorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.name = "vendorA";
        this.description = "I'm a vendor";
        this.vendor = new Vendor(name, description);
        this.category = new Category("CLO", "clothing");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCategories method, of class Vendor.
     */
    @Test
    public void testGetCategories() {
        System.out.println("getCategories");
        Vendor instance = this.vendor;
        Iterable<Category> result = instance.getCategories();
        assertNotNull(result);
    }

    /**
     * Test of addCategory method, of class Vendor.
     */
    @Test
    public void testAddCategory() {
        System.out.println("addCategory");
        Category category = this.category;
        Vendor instance = this.vendor;
        instance.addCategory(category);
    }

    /**
     * Test of removeCategory method, of class Vendor.
     */
    @Test
    public void testRemoveCategory() {
        System.out.println("removeCategory");
        Category category = this.category;
        Vendor instance = this.vendor;
        instance.addCategory(category);
        assertEquals(1, ((Collection)instance.getCategories()).size());
        instance.removeCategory(category);
        assertEquals(0, ((Collection)instance.getCategories()).size());
    }

    /**
     * Test of getKey method, of class Vendor.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        Vendor instance = this.vendor;
        String expResult = this.name;
        String result = instance.getKey();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getDescription method, of class Vendor.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Vendor instance = this.vendor;
        String expResult = this.description;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }
    
}
