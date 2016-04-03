/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.HashSet;
import java.util.List;
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
public class CategoryTest {
    
    private String name;
    private String id;
    private Category category;
    
    public CategoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.name = "clothing";
        this.id = "CLO";
        this.category = new Category(this.id, this.name);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProducts method, of class Category.
     */
    @Test
    public void testGetProducts() {
        System.out.println("getProducts");
        Category instance = this.category;
        Iterable<Product> result = instance.getProducts();
        assertNotNull(result);
    }

    /**
     * Test of getVendors method, of class Category.
     */
    @Test
    public void testGetVendors() {
        System.out.println("getVendors");
        Category instance = this.category;
        Iterable<Vendor> result = instance.getVendors();
        assertNotNull(result);
    }

    /**
     * Test of addProduct method, of class Category.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        Product p = new Product(this.category, 1);
        this.category.addProduct(p);
        int size  = ((HashSet)this.category.getProducts()).size();
        assertEquals(1, size);

    }

    /**
     * Test of removeProduct method, of class Category.
     */
    @Test
    public void testRemoveProduct() {
        System.out.println("removeProduct");
        Product p = new Product(this.category, 1);
        Category instance = this.category;
        instance.addProduct(p);
        assertEquals(1, ((HashSet)this.category.getProducts()).size());
        
        instance.removeProduct(p);
        assertEquals(0, ((HashSet)this.category.getProducts()).size());
        
        
    }

    /**
     * Test of addVendor method, of class Category.
     */
    @Test
    public void testAddVendor() {
        System.out.println("addVendor");
        Vendor v = new Vendor();
        Category instance = this.category;
        instance.addVendor(v);
        assertEquals(((HashSet)instance.getVendors()).size(), 1);
        
    }

    /**
     * Test of removeVendor method, of class Category.
     */
    @Test
    public void testRemoveVendor() {
        System.out.println("removeVendor");
        Vendor v = new Vendor();
        Category instance = this.category;
        instance.addVendor(v);
        assertEquals(((HashSet)instance.getVendors()).size(), 1);
        instance.removeVendor(v);
        assertEquals(((HashSet)instance.getVendors()).size(), 0);
    }
    /**
     * Test of setName method, of class Category.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "clotings";
        Category instance = this.category;
        instance.setName(name);
        assertEquals(this.category.getName(), name);
    }

    /**
     * Test of getKey method, of class Category.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        Category instance = this.category;
        String expResult = "CLO";
        String result = instance.getKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Category.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Category instance = this.category;
        String expResult = "clothing";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Category.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Category instance = this.category;
        String expResult = "clothing";
        String result = instance.toString();
        assertEquals(expResult, result);

    }
    
}
