/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Vendor;
import java.util.Collection;

/**
 *
 * @author Vishnu
 */
public interface IInventoryService {
    
    Category addCategory(String id, String name);
    
    void deleteCategory(String categoryId);

    Product addProduct(Category category, 
                        String name, 
                        String description, 
                        int availableQuantity,
                        int price,
                        String barcodeNumber,
                        int reorderQuantity,
                        int orderQuantity);
    
    void deleteProduct(String productId);
    
    
    void reorderProduct(Product product, Vendor vendor);
    
    /*Similar to wildcard search*/
    Collection<Product> searchProductByName(String name);
    
    Product searchProductByBarcode(String barcode);
    
    Collection<Product> getProductsBelowThreshold();
    
    Collection<Product> getProductsByCategoryId(String categoryId);
}
