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
            ex.printStackTrace();
        }
        assertNull(result);
    }

    /**
     * Test of ChangePassword method, of class UsersService.
     */
    @Test
    public void testChangePassword() throws Exception {
        System.out.println("ChangePassword");
        String username = "Zaid";
        String oldPassword = "123456";
        String newPassword = "654321";
        IUsersService instance = this.service;
        this.unitOfWork.add(new User(username, oldPassword));
        instance.ChangePassword(username, oldPassword, newPassword);
        assertEquals(instance.getUserByName(username).getPassword(),newPassword);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of addUser method, of class UsersService.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        String username = "Zaid";
        String password = "123456";
        IUsersService instance = this.service;
        User result = instance.addUser(username, password);
        assertNotNull(result);
    }

    /**
     * Test of deleteUser method, of class UsersService.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        String username = "Zaid";
        String password = "123456";
        IUsersService instance = this.service;
        assertNotNull(instance.addUser(username, password));
        instance.deleteUser(username);
        assertNull(instance.getUserByName("Ziad"));
        
    }

    /**
     * Test of getUserByName method, of class UsersService.
     */
    @Test
    public void testGetUserByName() throws Exception {
        System.out.println("getUserByName");
        String username = "Zaid";
        String password = "123456";
        IUsersService instance = this.service;
        service.addUser(username, password);
        User result = instance.getUserByName(username);
        result.verify(username, password);
    }

    /**
     * Test of checkPasswordComplexity method, of class UsersService.
     */
    @Test
    public void testCheckPasswordComplexity() throws Exception {
        System.out.println("checkPasswordComplexity");
        String password = "123456";
        IUsersService instance = this.service;
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
        expResult = false;
        String password1 = "123";
        result = instance.checkPasswordComplexity(password1);
        assertEquals(expResult, result);

    }
}
