package edu.nus.iss.pos.services;

import java.util.Collection;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Vendor;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IInventoryService;

public class InventoryService implements IInventoryService {

	private final IUnitOfWork unitOfWork;
    
    public InventoryService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
    
	@Override
	public Category addCategory(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategory(String categoryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product addProduct(Category category, String name, String description, int availableQuantity, int price,
			String barcodeNumber, int reorderQuantity, int orderQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(String productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reorderProduct(Product product, Vendor vendor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Product> searchProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product searchProductByBarcode(String barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Product> getProductsBelowThreshold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Product> getProductsByCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
