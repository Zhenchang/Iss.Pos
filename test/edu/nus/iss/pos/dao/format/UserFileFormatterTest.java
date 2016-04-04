/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.User;
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
public class UserFileFormatterTest {
    
    public UserFileFormatterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of getInstance method, of class UserFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        UserFileFormatter result = UserFileFormatter.getInstance();
        assertTrue(result instanceof UserFileFormatter);
    }

    /**
     * Test of format method, of class UserFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        User entity = new User("Zaid","123456");
        UserFileFormatter instance = UserFileFormatter.getInstance();
        String expResult = "Zaid,123456\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);

    }

    /**
     * Test of readEntity method, of class UserFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "Zaid,123456\n";
        UserFileFormatter instance = UserFileFormatter.getInstance();
        User expResult = new User("Zaid", "123456");
        User result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class UserFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "Zaid,123456\n";
        UserFileFormatter instance = UserFileFormatter.getInstance();
        String expResult = "Zaid";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
