/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
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
public class SalesServiceTest {
    
    private ISalesService service;
    private IUnitOfWork unitOfWork;
    
    public SalesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        this.unitOfWork = new MemoryUnitOfWork();
        this.service = new SalesService(unitOfWork);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of beginTransaction method, of class SalesService.
     */
    @Test
    public void testBeginTransaction() throws Exception {
        System.out.println("beginTransaction");
        Member member = new Member("test","test");
        ISalesService instance = this.service;
        Transaction expResult = null;
        Transaction result = instance.beginTransaction(member);
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
//
//    /**
//     * Test of addToCart method, of class SalesService.
//     */
//    @Test
//    public void testAddToCart() {
//        System.out.println("addToCart");
//        Transaction transaction = null;
//        Product product = null;
//        int quantity = 0;
//        SalesService instance = null;
//        TransactionDetail expResult = null;
//        TransactionDetail result = instance.addToCart(transaction, product, quantity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of checkout method, of class SalesService.
//     */
//    @Test
//    public void testCheckout() throws Exception {
//        System.out.println("checkout");
//        Transaction transaction = null;
//        float discount = 0.0F;
//        boolean useLoyaltyPoints = false;
//        SalesService instance = null;
//        instance.checkout(transaction, discount, useLoyaltyPoints);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPriceAfterDiscount method, of class SalesService.
//     */
//    @Test
//    public void testGetPriceAfterDiscount() throws Exception {
//        System.out.println("getPriceAfterDiscount");
//        Transaction transaction = null;
//        float discount = 0.0F;
//        SalesService instance = null;
//        float expResult = 0.0F;
//        float result = instance.getPriceAfterDiscount(transaction, discount);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFinalPrice method, of class SalesService.
//     */
//    @Test
//    public void testGetFinalPrice() throws Exception {
//        System.out.println("getFinalPrice");
//        Transaction transaction = null;
//        float discount = 0.0F;
//        boolean useLoyaltyPoints = false;
//        SalesService instance = null;
//        float expResult = 0.0F;
//        float result = instance.getFinalPrice(transaction, discount, useLoyaltyPoints);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
