/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.User;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IUsersService;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UsersServiceTest {

    private IUsersService service;
    private IUnitOfWork unitOfWork;

    public UsersServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        unitOfWork = new MemoryUnitOfWork();
        service = new UsersService(unitOfWork);
        System.out.println("Setup");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Login method, of class UsersService.
     */
    @Test
    public void testCorrectLogin() throws Exception {
        System.out.println("Correct Login");
        String username = "zaid";
        String password = "123456";
        User expResult = new User(username, password);
        unitOfWork.add(expResult);
        User result = service.Login(username, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFailedLogin() throws Exception {
        System.out.println("Failed Login");
        String username = "zaid1";
        String password = "***";
        User user = new User("zaid1", "123123");
        unitOfWork.add(user);
        User result = null;
        try {
            result = service.Login(username, password);
        } catch (Exception ex) {
        }
        assertNull(result);
    }
/*
    /**
     * Test of ChangePassword method, of class UsersService.
     */
//    @Test
//    public void testChangePassword() throws Exception {
//        System.out.println("ChangePassword");
//        String username = "";
//        String oldPassword = "";
//        String newPassword = "";
//        UsersService instance = null;
//        instance.ChangePassword(username, oldPassword, newPassword);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addUser method, of class UsersService.
//     */
//    @Test
//    public void testAddUser() throws Exception {
//        System.out.println("addUser");
//        String username = "";
//        String password = "";
//        UsersService instance = null;
//        User expResult = null;
//        User result = instance.addUser(username, password);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteUser method, of class UsersService.
//     */
//    @Test
//    public void testDeleteUser() throws Exception {
//        System.out.println("deleteUser");
//        String username = "";
//        UsersService instance = null;
//        instance.deleteUser(username);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUserByName method, of class UsersService.
//     */
//    @Test
//    public void testGetUserByName() throws Exception {
//        System.out.println("getUserByName");
//        String username = "";
//        UsersService instance = null;
//        User expResult = null;
//        User result = instance.getUserByName(username);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of checkPasswordComplexity method, of class UsersService.
//     */
//    @Test
//    public void testCheckPasswordComplexity() throws Exception {
//        System.out.println("checkPasswordComplexity");
//        String password = "";
//        UsersService instance = null;
//        boolean expResult = false;
//        boolean result = instance.checkPasswordComplexity(password);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
