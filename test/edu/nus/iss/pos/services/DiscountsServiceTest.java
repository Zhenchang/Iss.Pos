/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Discount;
import edu.nus.iss.pos.core.FirstPurchaseDiscount;
import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.PeriodDiscount;
import edu.nus.iss.pos.core.SubsequentPurchaseDiscount;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
import edu.nus.iss.pos.dao.format.RepoType;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Liu Zhenchang
 */
public class DiscountsServiceTest {
    
    private static IUnitOfWork unitOfWork;
    private static IRepository repository;
    
    public DiscountsServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(2015, 1, 1);
        unitOfWork = new MemoryUnitOfWork();
        repository = unitOfWork.getRepository(RepoType.Discount);
        Discount discount = new FirstPurchaseDiscount("first", "description", 80);
        Discount discount2 = new SubsequentPurchaseDiscount("subsequent", "description", 90);
        Discount discount3 = new PeriodDiscount("period", "description", 85, c.getTime(), 30, true);
        unitOfWork.add(discount);
        unitOfWork.add(discount2);
        unitOfWork.add(discount3);
        c.set(2016, 1, 1);
        Transaction transaction = new Transaction(10, c.getTime(), new Member("111", "jack"));
        unitOfWork.add(transaction);
        System.out.println("Setup");
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
     * Test of addDiscount method, of class DiscountsService.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddDiscount() throws Exception {
        System.out.println("addDiscount");
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1);
        Date startDate = c.getTime();
        Discount discount = new PeriodDiscount("code", "description", 99, startDate, 30, true);
        DiscountsService instance = new DiscountsService(unitOfWork);
        instance.addDiscount(discount);
        assertNotNull(repository.getByKey(discount.getCode()));
    }
    
//    /**
//     * Test of ifFirstDiscount method, of class DiscountsService.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testIfFirstDiscount() throws Exception {
//        System.out.println("ifFirstDiscount");
//        Calendar c = Calendar.getInstance();
//        c.set(2016, 1, 1);
//        Transaction transaction = new Transaction(1, c.getTime(), new Member("111", "jack"));
//        Transaction transaction2 = new Transaction(2, c.getTime(), new Member("222", "smith"));
//        unitOfWork.add(transaction);
//        DiscountsService instance = new DiscountsService(unitOfWork);
//        assertFalse(instance.ifFirstDiscount(transaction));
//        assertTrue(instance.ifFirstDiscount(transaction2));
//    }
    
    /**
     * Test of getOtherDiscount method, of class DiscountsService.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetOtherDiscount() throws Exception {
        System.out.println("testGetOtherDiscount");
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1);
        Transaction transaction = new Transaction(3, c.getTime(), new Member("111", "jack"));
        Transaction transaction2 = new Transaction(4, c.getTime(), new Member("222", "smith"));
        unitOfWork.add(transaction);
        DiscountsService instance = new DiscountsService(unitOfWork);
        int expResult = 80;
        int expResult2 = 90;
        assertEquals(expResult, instance.getDiscountForTransaction(transaction2));
        assertEquals(expResult2, instance.getDiscountForTransaction(transaction));
    }

    
//    /**
//     * Test of getPeriodDiscountForTransaction method, of class DiscountsService.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testGetPeriodDiscountForTransaction() throws Exception {
//        System.out.println("getPeriodDiscountForTransaction");
//        Calendar c = Calendar.getInstance();
//        c.set(2015, 1, 15);
//        Transaction transaction = new Transaction(5, c.getTime(), new Member("111", "jack"));
//        c.set(2015, 3, 15);
//        Transaction transaction2 = new Transaction(6, c.getTime(), new Member("111", "jack"));
//        DiscountsService instance = new DiscountsService(unitOfWork);
//        int expResult = 85;
//        assertEquals(expResult, instance.getPeriodDiscountForTransaction(transaction));
//        assertNotEquals(expResult, instance.getPeriodDiscountForTransaction(transaction2));
//    }
    
    /**
     * Test of getDiscountForTransaction method, of class DiscountsService.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDiscountForTransaction() throws Exception {
        System.out.println("getDiscountForTransaction");
        Calendar c = Calendar.getInstance();
        c.set(2015, 1, 15);
        Transaction transaction = new Transaction(7, c.getTime(), new Member("111", "jack"));
        c.set(2015, 3, 15);
        Transaction transaction2 = new Transaction(8, c.getTime(), new Member("322", "harry"));
        Transaction transaction3 = new Transaction(7, c.getTime(), new Member("111", "jack"));
        DiscountsService instance = new DiscountsService(unitOfWork);
        int result = instance.getDiscountForTransaction(transaction);
        assertEquals(90, instance.getDiscountForTransaction(transaction));
        assertEquals(80, instance.getDiscountForTransaction(transaction2));
        assertEquals(90, instance.getDiscountForTransaction(transaction3));
    }

//    /**
//     * Test of applyDiscountForTransaction method, of class DiscountsService.
//     */
//    @Test
//    public void testApplyDiscountForTransaction() {
//        System.out.println("applyDiscountForTransaction");
//        Transaction transaction = null;
//        DiscountsService instance = null;
//        instance.applyDiscountForTransaction(transaction);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
