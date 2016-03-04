/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.repositories;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Vendor;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.FileType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author zz
 */
public class VendorRepository extends Repository<Vendor>{
    
    private String fileNamePrefix = "";
    public VendorRepository(IUnitOfWork unitOfWork, String fileNamePrefix) throws Exception {
        super(unitOfWork, FileType.Vendor, "");
        this.fileNamePrefix = fileNamePrefix;
        
    }
    
    @Override
    public void add(Vendor entity) throws Exception {
        for(Category category : entity.getCategories()){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.update(entity.getKey(), entity);
        }
    }
    
    @Override
    public void update(String oldKey, Vendor entity) throws Exception {
        Iterable<Category> categories = this.getUnitOfWork().getRepository(FileType.Category).getAll();
        Iterable<Category> vendorCategories = entity.getCategories();
        List<Category> shouldBeDeleted = new ArrayList();
        for(Category category : categories){
            for(Category venderCategory : vendorCategories){
                if(!category.getKey().equals(venderCategory.getKey())){
                    shouldBeDeleted.add(category);
                    break;
                }
            }
            
        }
        for(Category category : shouldBeDeleted){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.delete(entity);
        }
        for(Category category : vendorCategories){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.update(oldKey, entity);
        }
    }
    
    @Override
    public void delete(Vendor entity) throws Exception {
        Iterable<Category> categories = this.getUnitOfWork().getRepository(FileType.Category).getAll();
        for(Category category : categories){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.delete(entity);
        }
    }
    
    @Override
    public Iterable<Vendor> getAll() throws Exception {
        List<Vendor> vendors = new ArrayList();
        Iterable<Category> categories = this.getUnitOfWork().getRepository(FileType.Category).getAll();
        for(Category category : categories){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.getAll().forEach((v)->{
                int index = vendors.indexOf(v);
                if(index == -1){
                    v.addCategory(category);
                    vendors.add(v);
                }else{
                    vendors.get(index).addCategory(category);
                }
            });
        }
        return vendors;
    }
    @Override
    public Vendor getByKey(String key) throws Exception {
        for(Vendor v : getAll()){
            if(v.getKey().equals(key)) return v;
        }
        return null;
    }
}
