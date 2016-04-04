/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Category;
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
public class CategoryFileFormatterTest {
    
    private Category category;
    private String name;
    private String id;
    
    public CategoryFileFormatterTest() {
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
        this.id = "ClO";
        this.category = new Category(this.id, this.name);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class CategoryFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CategoryFileFormatter result = CategoryFileFormatter.getInstance();
        assertTrue(result instanceof CategoryFileFormatter);
    }

    /**
     * Test of readEntity method, of class CategoryFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "CLO,clothing";
        CategoryFileFormatter instance = CategoryFileFormatter.getInstance();
        Category result = instance.readEntity(data);
        assertEquals("CLO", result.getKey());
        assertEquals("clothing", result.getName());

    }

    /**
     * Test of format method, of class CategoryFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Category entity = new Category("CLO", "clothing");
        CategoryFileFormatter instance = CategoryFileFormatter.getInstance();
        String expResult = "CLO,clothing\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);

    }

    /**
     * Test of getKey method, of class CategoryFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "CLO,clothing";
        CategoryFileFormatter instance = CategoryFileFormatter.getInstance();
        String expResult = "CLO";
        String result = instance.getKey(data);
        assertEquals(expResult, result);

    }
    
}
