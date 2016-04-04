/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import java.util.Date;
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
public class TransactionFileFormatterTest {
    
    private UnitOfWork unitOfWork;
    
    public TransactionFileFormatterTest() {
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
     * Test of getInstance method, of class TransactionFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        TransactionFileFormatter result = TransactionFileFormatter.getInstance(unitOfWork);
        assertTrue(result instanceof TransactionFileFormatter);
    }

    /**
     * Test of format method, of class TransactionFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Transaction entity = new Transaction(1, new Date(116,03,03), Customer.getInstance());
        TransactionFileFormatter instance = TransactionFileFormatter.getInstance(unitOfWork);
        String expResult = "1,CLO/1,PUBLIC,1,2016-04-03\n";
        Category category = new Category("CLO", "clothing");
        Product product = new Product(category, 1, "clothing", "this is a description", 100, (float) 50.0, "100", 100, 100);
        TransactionDetail transactionDetial = new TransactionDetail(entity, product, 1);
        entity.addTransactionDetail(transactionDetial);
        String result = instance.format(entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of readEntity method, of class TransactionFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "1,CLO/1,PUBLIC,1,2016-4-3";
        TransactionFileFormatter instance = TransactionFileFormatter.getInstance(unitOfWork);
        Transaction expResult = new Transaction(1, new Date(), Customer.getInstance());
        Transaction result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class TransactionFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "1,CLO/1,PUBLIC,1,2016-4-3";
        TransactionFileFormatter instance = TransactionFileFormatter.getInstance(unitOfWork);
        String expResult = "1";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
