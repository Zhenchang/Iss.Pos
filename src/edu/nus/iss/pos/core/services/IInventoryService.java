/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import java.util.Collection;

/**
 *
 * @author Vishnu
 */
public interface IInventoryService {
    
    Category addCategory(String id, String name) throws Exception;
    
    Iterable<Category> getAllCategory() throws Exception;
    
    void deleteCategory(String categoryId) throws Exception;

    Product addProduct(Category category, 
                        String name, 
                        String description, 
                        int availableQuantity,
                        float price,
                        String barcodeNumber,
                        int reorderQuantity,
                        int orderQuantity) throws Exception;
    
    void deleteProduct(String productId) throws Exception;
    
    
    void reorderProduct(Product product) throws Exception;
    
    /*Similar to wildcard search*/
    Collection<Product> searchProductByName(String name) throws Exception;
    
    Product searchProductByBarcode(String barcode) throws Exception;
    
    Collection<Product> getProductsBelowThreshold() throws Exception;
    
    Collection<Product> getProductsByCategoryId(String categoryId) throws Exception;
    
    public void updateProduct(Product product) throws Exception;
    
    public void updateCategory(Category category) throws Exception;
}
