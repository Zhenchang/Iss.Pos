/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Date;
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
public class TransactionDetailTest {
    
    private TransactionDetail transactionDetail;
    private Product product;
    private Category category;
    private Transaction transaction;
    
    public TransactionDetailTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        this.category = new Category("CLO", "clothing");
        this.product = new Product(this.category, 1, "productA", "this is a product",  100, 100, "111", 100, 100);
        this.transaction = new Transaction(1, new Date(), new Customer());
        this.transactionDetail = new TransactionDetail(this.transaction, this.product, 1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getQuantityPurchased method, of class TransactionDetail.
     */
    @Test
    public void testGetQuantityPurchased() {
        System.out.println("getQuantityPurchased");
        TransactionDetail instance = this.transactionDetail;
        int expResult = 1;
        int result = instance.getQuantityPurchased();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuantityPurchased method, of class TransactionDetail.
     */
    @Test
    public void testSetQuantityPurchased() {
        System.out.println("setQuantityPurchased");
        int quantityPurchased = 2;
        TransactionDetail instance = this.transactionDetail;
        instance.setQuantityPurchased(quantityPurchased);
        assertEquals(quantityPurchased, this.transactionDetail.getQuantityPurchased());
    }

    /**
     * Test of getProduct method, of class TransactionDetail.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        TransactionDetail instance = this.transactionDetail;
        Product expResult = this.product;
        Product result = instance.getProduct();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProduct method, of class TransactionDetail.
     */
    @Test
    public void testSetProduct() {
        System.out.println("setProduct");
        Product product = new Product(this.category, 2, "productA", "this is a product",  100, 100, "111", 100, 100);
        TransactionDetail instance = this.transactionDetail;
        instance.setProduct(product);
        assertEquals(instance.getProduct(), product);
    }

    /**
     * Test of getTransaction method, of class TransactionDetail.
     */
    @Test
    public void testGetTransaction() {
        System.out.println("getTransaction");
        TransactionDetail instance = this.transactionDetail;
        Transaction expResult = this.transaction;
        Transaction result = instance.getTransaction();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTransaction method, of class TransactionDetail.
     */
    @Test
    public void testSetTransaction() {
        System.out.println("setTransaction");
        Transaction transaction = new Transaction(2, new Date(), new Customer());
        TransactionDetail instance = this.transactionDetail;
        instance.setTransaction(transaction);
        assertEquals(instance.getTransaction(), transaction);
    }
    
}
