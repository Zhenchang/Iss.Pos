/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.CustomerTest;
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
    private int id;
    private int index;
    private Date date;
    private Customer customer;
    private Transaction transaction;
    private Product product;
    private Category category;
    private TransactionDetail transactionDetail;
    
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
        id=3;
        index=3;
        date = new Date();
        category= new Category("CLO", "colthing");
        customer= Customer.getInstance();
        transaction = new Transaction(1, date , customer);
        product = new Product(category, index, "Clothes", "Really Nice",327, id,"558944", 56, 100);
        int quantity = 3;
        TransactionDetail result = service.addToCart(transaction, product, quantity);
        assertNotNull(result);
    }

    /**
     * Test of checkout method, of class SalesService.
     */
    @Test
    public void testCheckout() throws Exception {
        System.out.println("checkout");
        date = new Date();
        customer = new Member("Zaid", "111", 150);
        category = new Category("CLO", "colthing");
        index = 3;
        product = new Product(category, index, "Clothes", "Really Nice",100, 100,"558944", 100, 100);
        transaction = new Transaction(1, date, customer);
        transactionDetail=new TransactionDetail(transaction,product, 3);
        transaction.addTransactionDetail(transactionDetail);
        int discount = 20;
        service.checkout(transaction, discount, 0);
        assertEquals(product.getQuantity(), 97);
    }

    /**
     * Test of getPriceAfterDiscount method, of class SalesService.
     */
    @Test
    public void testGetPriceAfterDiscount() throws Exception {
        System.out.println("getPriceAfterDiscount");
        date = new Date();
        customer= Customer.getInstance();
        category= new Category("CLO", "colthing");
        index=3;
        product = new Product(category, index, "Clothes", "Really Nice",327, 100,"558944", 56, 100);
        transaction = new Transaction(1, date, customer);
        transactionDetail=new TransactionDetail(transaction,product, 3);
        transaction.addTransactionDetail(transactionDetail);
        int discount = 20;
        float price = transaction.getTotalWithoutDiscount();
        float expResult = (price-price*((float)discount/100));
        float result = service.getPriceAfterDiscount(transaction, discount);
        assertEquals(expResult, result,0.0F);       
    }

    /**
     * Test of getFinalPrice method, of class SalesService.
     */
    @Test
    public void testGetFinalPrice() throws Exception {
        System.out.println("getFinalPrice");
        date = new Date();
        customer= Customer.getInstance();
        transaction = new Transaction(1, date, customer);
        category= new Category("CLO", "clothing");
        index = 1;
        product = new Product(category, index, "Clothes", "Really Nice",100, 100,"100", 100, 100);
        transactionDetail = new TransactionDetail(transaction, product, 3);
        transaction.addTransactionDetail(transactionDetail);
        int discount = 20;
        float result = service.getFinalPrice(transaction, discount, 0);
        System.out.println(result);
        assertTrue(result == 240);
    }
    
}
