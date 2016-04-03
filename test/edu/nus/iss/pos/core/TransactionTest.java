/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

import java.util.Date;
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
public class TransactionTest {
    
    private int id;
    private Date date;
    private Customer customer;
    private Transaction transaction;
    private Product product;
    private Category category;
    private TransactionDetail transactionDetail;
    
    public TransactionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.id = 1;
        this.date = new Date();
        this.customer = new Customer();
        this.transaction = new Transaction(this.id, this.date, this.customer);
        this.category = new Category("CLO", "clothing");
        this.product = new Product(this.category, 1, "productA", "this is a product",  100, 100, "111", 100, 100);
        this.transactionDetail = new TransactionDetail(this.transaction, this.product, 1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getKey method, of class Transaction.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        Transaction instance = this.transaction;
        String expResult = String.valueOf(this.id);
        String result = instance.getKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransactionDetails method, of class Transaction.
     */
    @Test
    public void testGetTransactionDetails() {
        System.out.println("getTransactionDetails");
        Transaction instance = this.transaction;
        List<TransactionDetail> result = instance.getTransactionDetails();
        assertTrue(result instanceof List);
    }

    /**
     * Test of getCustomer method, of class Transaction.
     */
    @Test
    public void testGetCustomer() {
        System.out.println("getCustomer");
        Transaction instance = this.transaction;
        Customer expResult = this.customer;
        Customer result = instance.getCustomer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class Transaction.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Transaction instance = this.transaction;
        Date expResult = this.date;
        Date result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDate method, of class Transaction.
     */
    @Test
    public void testSetDate() {
        System.out.println("setDate");
        Date date = new Date();
        Transaction instance = this.transaction;
        instance.setDate(date);
        assertEquals(date, instance.getDate());
    }

    /**
     * Test of setCustomer method, of class Transaction.
     */
    @Test
    public void testSetCustomer() {
        System.out.println("setCustomer");
        Customer customer = this.customer;
        Transaction instance = this.transaction;
        instance.setCustomer(customer);
        assertEquals(this.customer, instance.getCustomer());
    }

    /**
     * Test of getId method, of class Transaction.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Transaction instance = this.transaction;
        int expResult = this.id;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of addTransactionDetail method, of class Transaction.
     */
    @Test
    public void testAddTransactionDetail() {
        System.out.println("addTransactionDetail");
        TransactionDetail transactionDetail = this.transactionDetail;
        Transaction instance = this.transaction;
        instance.addTransactionDetail(transactionDetail);
        assertTrue(instance.getTransactionDetails().size() == 1);
    }

    /**
     * Test of removeTransactionDetail method, of class Transaction.
     */
    @Test
    public void testRemoveTransactionDetail() {
        System.out.println("removeTransactionDetail");
        TransactionDetail transactionDetail = this.transactionDetail;
        Transaction instance = this.transaction;
        instance.addTransactionDetail(transactionDetail);
        assertTrue(instance.getTransactionDetails().size() == 1);
        instance.removeTransactionDetail(transactionDetail);
        assertTrue(instance.getTransactionDetails().size() == 0);
    }

    /**
     * Test of getTotalWithoutDiscount method, of class Transaction.
     */
    @Test
    public void testGetTotalWithoutDiscount() {
        System.out.println("getTotalWithoutDiscount");
        Transaction instance = this.transaction;
        instance.addTransactionDetail(this.transactionDetail);
        float expResult = 100F;
        float result = instance.getTotalWithoutDiscount();
        assertEquals(expResult, result, 0.0);
    }
    
}
