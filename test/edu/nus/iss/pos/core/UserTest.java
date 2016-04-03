/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core;

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
public class UserTest {
    
    private String username;
    private String password;
    private User user;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.username = "Zaid";
        this.password = "123456";
        this.user = new User(username, password);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getKey method, of class User.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        User instance = this.user;
        String expResult = "Zaid";
        String result = instance.getKey();
        assertEquals(expResult, result);

    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = this.user;
        String expResult = "Zaid";
        String result = instance.getUsername();
        assertEquals(expResult, result);

    }

    /**
     * Test of setUsername method, of class User.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "zaid";
        User instance = this.user;
        instance.setUsername(username);
        String expResult = instance.getUsername();
        assertEquals(username, expResult);
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = this.user;
        String expResult = "123456";
        String result = instance.getPassword();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "654321";
        User instance = this.user;
        instance.setPassword(password);
        String expResult = this.user.getPassword();
        assertEquals(password, expResult);
    }

    /**
     * Test of verify method, of class User.
     */
    @Test
    public void testVerify() {
        System.out.println("verify");
        String username = "Zaid";
        String password = "123456";
        User instance = this.user;
        boolean expResult = true;
        boolean result = instance.verify(username, password);
        assertEquals(expResult, result);
        password = "654321";
        result = instance.verify(username, password);
        assertNotSame(expResult, result);
    }
    
}
