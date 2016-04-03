/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IMembershipService;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
import edu.nus.iss.pos.dao.format.RepoType;
import java.time.Instant;
import java.util.Date;
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
public class SalesServiceTest {
    
    private IUnitOfWork unitOfWork;
    private ISalesService service;
    
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
        unitOfWork = new MemoryUnitOfWork();
        service = new SalesService(unitOfWork);
        System.out.println("Setup");
        
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
        String id= "E552121R";
        String name="Ankan";
        Member member = new Member(id, name);
        service = new SalesService(unitOfWork);
        Transaction result = service.beginTransaction(member);
        assertNotNull(result);
    }

    /**
     * Test of addToCart method, of class SalesService.
     */
    @Test
    public void testAddToCart()throws Exception {
        System.out.println("addToCart");
        int id=3;
        int index=3;
        Date date = new Date();
        Category category= new Category("CLO", "colthing");
        Customer customer= new Customer();
        Transaction transaction = new Transaction(1, date , customer);
        Product product = new Product(category, index, "Clothes", "Really Nice",327, id,"558944", 56, 100);
        int quantity = 3;
        TransactionDetail result = service.addToCart(transaction, product, quantity);
        assertNotNull(result);
    }
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
