/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IMembershipService;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
import edu.nus.iss.pos.dao.format.RepoType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MyLappy
 */
public class MembershipServiceTest {
    private IUnitOfWork unitOfWork;
    private IMembershipService service;
    
    public MembershipServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        unitOfWork = new MemoryUnitOfWork();
        service= new MembershipService(unitOfWork);
        System.out.println("Setup");
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerMember method, of class MembershipService.
     * @throws java.lang.Exception
     */
    @Test
    public void testRegisterMember() throws Exception {
        System.out.println("registerMember");
        String id = "E45667765";
        String name = "Ankan";
        Member result = service.registerMember(id, name); 
        Member expResult= (Member)unitOfWork.getRepository(RepoType.Member).getByKey(id);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of searchMemberByName method, of class MembershipService.
     */
    @Test
    public void testSearchMemberByName() throws Exception {
        System.out.println("searchMemberByName");
        String name = "Monalisha";
        String id = "02245200012";
        Member expResult=new Member(id,name);
        unitOfWork.add(expResult);
        Member result =service.searchMemberByName(name);
        assertEquals(expResult, result);      
    }

    /**
     * Test of searchMemberById method, of class MembershipService.
     */
    @Test
    public void testSearchMemberById() throws Exception {
        System.out.println("searchMemberById");
        String id = "EEFF3437456";
        String name= "Simmons";  
        Member expResult=new Member(id,name);
        unitOfWork.add(expResult);
        Member result= service.searchMemberById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateMember method, of class MembershipService.
     */
    @Test
    public void testUpdateMember() throws Exception {
        System.out.println("updateMember");
        String id="EER111222";
        String name="Maron";
        Member member = new Member(id,name);
        unitOfWork.add(member);
        Member expResult= (Member)unitOfWork.getRepository(RepoType.Member).getByKey("EER111222");
        expResult.setName("Jack");
        service.updateMember(expResult);
        Member result= (Member)unitOfWork.getRepository(RepoType.Member).getByKey("EER111222");
        assertNotEquals(expResult, result.getName());       
    }

    /**
     * Test of getAllMembers method, of class MembershipService.
     */
    @Test
    public void testGetAllMembers() throws Exception {
        System.out.println("getAllMembers");
        Iterable<Member> result = service.getAllMembers();
        assertNotNull(result);  
    }    
}
