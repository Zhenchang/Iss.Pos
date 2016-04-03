/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Date;
import java.util.HashSet;
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
public class ProductTest {
    
    private Product product;
    
    private String id;
    private String name;
    private String description;
    private int availableQuantity;
    private float price;
    private String barcodeNumber;
    private int reorderQuantity;
    private int orderQuantity; 
    
    private Category category;
    
    public ProductTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        int index = 1;
        this.name = "good clothing";
        this.description = "this is a good clothing";
        this.availableQuantity = 100;
        this.price = (float) 100;
        this.barcodeNumber = "111";
        this.reorderQuantity = 100;
        this.orderQuantity = 100;
        
        this.category = new Category("CLO", "clothing");
        this.product = new Product(this.category, index,  name,  description,  availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCategoryId method, of class Product.
     */
    @Test
    public void testGetCategoryId() {
        System.out.println("getCategoryId");
        Product instance = this.product;
        String expResult = "CLO";
        String result = instance.getCategoryId();
        assertEquals(expResult, result);
    }

    /**
     * Test of addTransaction method, of class Product.
     */
    @Test
    public void testAddTransaction() {
        System.out.println("addTransaction");
        Transaction t = new Transaction(1, new Date(), new Customer());
        Product instance = this.product;
        instance.addTransaction(t);
        assertEquals(1, ((HashSet)instance.getTransactions()).size());
    }

    /**
     * Test of removeTransaction method, of class Product.
     */
    @Test
    public void testRemoveTransaction() {
        System.out.println("removeTransaction");
        System.out.println("addTransaction");
        Transaction t = new Transaction(1, new Date(), new Customer());
        Product instance = this.product;
        instance.addTransaction(t);
        assertEquals(1, ((HashSet)instance.getTransactions()).size());
        instance.removeTransaction(t);
        assertEquals(0, ((HashSet)instance.getTransactions()).size());
    }

    /**
     * Test of getTransactions method, of class Product.
     */
    @Test
    public void testGetTransactions() {
        System.out.println("getTransactions");
        Product instance = this.product;
        Iterable<Transaction> result = instance.getTransactions();
        assertNotNull(result);
        
    }

    /**
     * Test of setName method, of class Product.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "good clothing";
        Product instance = this.product;
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of setDescription method, of class Product.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "this is a good clothings";
        Product instance = this.product;
        instance.setDescription(description);
        assertEquals(instance.getDescription(), description);
        
    }

    /**
     * Test of setQuantity method, of class Product.
     */
    @Test
    public void testSetQuantity() {
        System.out.println("setQuantity");
        int quantity = 200;
        Product instance = this.product;
        instance.setQuantity(quantity);
        assertEquals(quantity, instance.getQuantity());
    }

    /**
     * Test of setPrice method, of class Product.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        float price = 200F;
        Product instance = this.product;
        instance.setPrice(price);
        assertTrue(price == instance.getPrice());
    }

    /**
     * Test of setBarcodeNumber method, of class Product.
     */
    @Test
    public void testSetBarcodeNumber() {
        System.out.println("setBarcodeNumber");
        String barcodeNumber = "222";
        Product instance = this.product;
        instance.setBarcodeNumber(barcodeNumber);
        assertEquals(barcodeNumber, instance.getBarcodeNumber());

    }

    /**
     * Test of setReorderQuantity method, of class Product.
     */
    @Test
    public void testSetReorderQuantity() {
        System.out.println("setReorderQuantity");
        int reorderQuantity = 100;
        Product instance = this.product;
        instance.setReorderQuantity(reorderQuantity);
        assertEquals(reorderQuantity, instance.getReorderQuantity());
    }

    /**
     * Test of setOrderQuantity method, of class Product.
     */
    @Test
    public void testSetOrderQuantity() {
        System.out.println("setOrderQuantity");
        int orderQuantity = 200;
        Product instance = this.product;
        instance.setOrderQuantity(orderQuantity);
        assertEquals(orderQuantity, instance.getOrderQuantity());
    }

    /**
     * Test of getKey method, of class Product.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        Product instance = this.product;
        String expResult = "CLO/1";
        String result = instance.getKey();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getName method, of class Product.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Product instance = this.product;
        String expResult = this.name;
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getDescription method, of class Product.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Product instance = this.product;
        String expResult = this.description;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuantity method, of class Product.
     */
    @Test
    public void testGetQuantity() {
        System.out.println("getQuantity");
        Product instance = this.product;
        int expResult = this.availableQuantity;
        int result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrice method, of class Product.
     */
    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        Product instance = this.product;
        float expResult = this.price;
        float result = instance.getPrice();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getBarcodeNumber method, of class Product.
     */
    @Test
    public void testGetBarcodeNumber() {
        System.out.println("getBarcodeNumber");
        Product instance = this.product;
        String expResult = this.barcodeNumber;
        String result = instance.getBarcodeNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReorderQuantity method, of class Product.
     */
    @Test
    public void testGetReorderQuantity() {
        System.out.println("getReorderQuantity");
        Product instance = this.product;
        int expResult = this.reorderQuantity;
        int result = instance.getReorderQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrderQuantity method, of class Product.
     */
    @Test
    public void testGetOrderQuantity() {
        System.out.println("getOrderQuantity");
        Product instance = this.product;
        int expResult = this.orderQuantity;
        int result = instance.getOrderQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCategory method, of class Product.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        Product instance = this.product;
        Category expResult = this.category;
        Category result = instance.getCategory();
        assertEquals(expResult, result);
    }
    
}
