/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
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
public class ProductFileFormatterTest {
    
    private IUnitOfWork unitOfWork;
    
    public ProductFileFormatterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        this.unitOfWork = new UnitOfWork();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ProductFileFormatter.
     */
    @Test
    public void testGetInstance() throws Exception {
        System.out.println("getInstance");
        unitOfWork = new UnitOfWork();
        ProductFileFormatter result = ProductFileFormatter.getInstance(unitOfWork);
        assertTrue(result instanceof ProductFileFormatter);
    }

    /**
     * Test of format method, of class ProductFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Category category = new Category("CLO","clothing");
        Product entity = new Product(category, 1, "clothing", "this is a description", 100, (float) 50.0, "100", 100, 100);
        ProductFileFormatter instance = ProductFileFormatter.getInstance(unitOfWork);
        String expResult = "CLO/1,clothing,this is a description,100,50.0,100,100,100\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of readEntity method, of class ProductFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        Category category = new Category("CLO","clothing");
        String data = "CLO/1,colthing,this is a description,100,50,100,100,100";
        ProductFileFormatter instance = ProductFileFormatter.getInstance(unitOfWork);
        Product expResult = new Product(category, 1, "clothing", "this is a description", 100, 50, "100", 100, 100);
        Product result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class ProductFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        Category category = new Category("CLO","clothing");
        String data = "CLO/1,colthing,this is a description,100,50,100,100,100\n";
        ProductFileFormatter instance = ProductFileFormatter.getInstance(unitOfWork);
        String expResult = "CLO/1";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
