/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.dao.MemoryUnitOfWork;
import java.util.Collection;
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
public class InventoryServiceTest {
    
    private IUnitOfWork unitOfWork;
    private IRepository repository;
    private IInventoryService service;
    
    public InventoryServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        this.unitOfWork = new MemoryUnitOfWork();
        this.service = new InventoryService(unitOfWork);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCategory method, of class InventoryService.
     */
    @Test
    public void testAddCategory() throws Exception {
        System.out.println("addCategory");
        String id = "CLO";
        String name = "clothing";
        IInventoryService instance = this.service;
        Category expResult = new Category(id, name);
        Category result = instance.addCategory(id, name);
        assertEquals(result.getName(), expResult.getName());
        assertEquals(result.getKey(), expResult.getKey());
    }

    /**
     * Test of deleteCategory method, of class InventoryService.
     */
    @Test
    public void testDeleteCategory() throws Exception {
        System.out.println("deleteCategory");
        String categoryId = "CLO";
        IInventoryService instance = this.service;
        instance.addCategory(categoryId, "clothing");
        assertTrue(((Collection)instance.getAllCategory()).size() == 1);
        instance.deleteCategory(categoryId);
        assertTrue(((Collection)instance.getAllCategory()).size() == 0);
    }

    /**
     * Test of addProduct method, of class InventoryService.
     */
    @Test
    public void testAddProduct() throws Exception {
        System.out.println("addProduct");
        Category category = new Category("CLO", "clothing");
        String name = "paroductA";
        String description = "It's a product";
        int availableQuantity = 100;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product result = instance.addProduct(category, name, description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        assertEquals(name, result.getName());
        assertTrue(instance.getProductsByCategoryId("CLO").size() == 1);
    }

    /**
     * Test of updateProduct method, of class InventoryService.
     */
    @Test
    public void testUpdateProduct() throws Exception {
        System.out.println("updateProduct");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 100;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product result = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        instance.addProduct(category, name, description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        instance.updateProduct(result);
        assertTrue(instance.getProductsByCategoryId("CLO").contains(result));
    }

    /**
     * Test of updateCategory method, of class InventoryService.
     */
    @Test
    public void testUpdateCategory() throws Exception {
        System.out.println("updateCategory");
        Category category = new Category("CLO", "clothings");
        IInventoryService instance = this.service;
        instance.addCategory("CLO", "clothing");
        instance.updateCategory(category);
        for(Category c : instance.getAllCategory()){
            if(c.getKey().equals(category.getKey()) && c.getName().equals(category.getName())){
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

    /**
     * Test of deleteProduct method, of class InventoryService.
     */
    @Test
    public void testDeleteProduct() throws Exception {
        System.out.println("deleteProduct");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 100;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product result = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        assertTrue(instance.getProductsByCategoryId("CLO").size() == 1);
        instance.deleteProduct("CLO/1");
        assertTrue(instance.getProductsByCategoryId("CLO").size() == 0);
    }

    /**
     * Test of reorderProduct method, of class InventoryService.
     */
    @Test
    public void testReorderProduct() throws Exception {
        System.out.println("reorderProduct");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 90;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product result = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        instance.reorderProduct(result);
        assertTrue(result.getQuantity() == 190);

    }

    /**
     * Test of searchProductByName method, of class InventoryService.
     */
    @Test
    public void testSearchProductByName() throws Exception {
        System.out.println("searchProductByName");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 100;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product product = instance.addProduct(category, "productA", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        Collection<Product> result = instance.searchProductByName(name);
        assertTrue(result.contains(product));

    }

    /**
     * Test of searchProductByBarcode method, of class InventoryService.
     */
    @Test
    public void testSearchProductByBarcode() throws Exception {
        System.out.println("searchProductByBarcode");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 100;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product product = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        Product result = instance.searchProductByBarcode(barcodeNumber);
        assertEquals(product, result);

    }

    /**
     * Test of getProductsBelowThreshold method, of class InventoryService.
     */
    @Test
    public void testGetProductsBelowThreshold() throws Exception {
        System.out.println("getProductsBelowThreshold");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 90;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product product = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        Collection<Product> result = instance.getProductsBelowThreshold();
        assertTrue(result.contains(product));
    }

    /**
     * Test of getProductsByCategoryId method, of class InventoryService.
     */
    @Test
    public void testGetProductsByCategoryId() throws Exception {
        System.out.println("getProductsByCategoryId");
        Category category = new Category("CLO", "clothing");
        String name = "productA";
        String description = "It's a product";
        int availableQuantity = 90;
        float price = 100F;
        String barcodeNumber = "111";
        int reorderQuantity = 100;
        int orderQuantity = 100;
        IInventoryService instance = this.service;
        Product product = instance.addProduct(category, "productN", description, availableQuantity, price, barcodeNumber, reorderQuantity, orderQuantity);
        Collection<Product> result = instance.getProductsByCategoryId("CLO");
        assertTrue(result.contains(product));

    }

    /**
     * Test of getAllCategory method, of class InventoryService.
     */
    @Test
    public void testGetAllCategory() throws Exception {
        System.out.println("getAllCategory");
        IInventoryService instance = this.service;
        instance.addCategory("CLO", "clothing");
        Iterable<Category> result = instance.getAllCategory();
        assertTrue(((Collection)result).size() == 1);
    }
    
}
