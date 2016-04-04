/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.format;

import edu.nus.iss.pos.core.Member;
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
public class MemberFileFormatterTest {
    
    public MemberFileFormatterTest() {
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
     * Test of getInstance method, of class MemberFileFormatter.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MemberFileFormatter result = MemberFileFormatter.getInstance();
        assertTrue(result instanceof MemberFileFormatter);
    }

    /**
     * Test of format method, of class MemberFileFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        Member entity = new Member("111", "Zaid", 150);
        MemberFileFormatter instance =  MemberFileFormatter.getInstance();
        String expResult = "Zaid,111,150\n";
        String result = instance.format(entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of readEntity method, of class MemberFileFormatter.
     */
    @Test
    public void testReadEntity() throws Exception {
        System.out.println("readEntity");
        String data = "Zaid,111,150\n";
        MemberFileFormatter instance =  MemberFileFormatter.getInstance();
        Member expResult = new Member("111", "Zaid", 150);
        Member result = instance.readEntity(data);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of getKey method, of class MemberFileFormatter.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");
        String data = "Zaid,111,150\n";
        MemberFileFormatter instance =  MemberFileFormatter.getInstance();
        String expResult = "111";
        String result = instance.getKey(data);
        assertEquals(expResult, result);
    }
    
}
