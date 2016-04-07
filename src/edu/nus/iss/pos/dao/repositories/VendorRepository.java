/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.repositories;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Vendor;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.FileFormatterFactory;
import edu.nus.iss.pos.dao.format.RepoType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zz
 */
public class VendorRepository extends Repository<Vendor>{
    
    private String fileNamePrefix = "";
    
    
    public VendorRepository(IUnitOfWork unitOfWork, String fileNamePrefix) throws Exception {
        super(unitOfWork, RepoType.Vendor, "");
        this.fileNamePrefix = fileNamePrefix;
        
    }
    
    @Override
    public void add(Vendor entity) throws Exception {
        for(Category category : entity.getCategories()){
            setFileName(fileNamePrefix + category.getKey() + ".dat");
            update(entity.getKey(), entity);
        }
    }
    
    @Override
    public void update(String oldKey, Vendor entity) throws Exception {
        Iterable<Category> categories = this.getUnitOfWork().getRepository(RepoType.Category).getAll();
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
            vendorUpdate(oldKey, entity);
        }
    }
    
    @Override
    public void delete(Vendor entity) throws Exception {
        Iterable<Category> categories = this.getUnitOfWork().getRepository(RepoType.Category).getAll();
        for(Category category : categories){
            this.setFileName(fileNamePrefix + category.getKey() + ".dat");
            super.delete(entity);
        }
    }
    
    @Override
    public Iterable<Vendor> getAll() throws Exception {
        List<Vendor> vendors = new ArrayList();
        Iterable<Category> categories = this.getUnitOfWork().getRepository(RepoType.Category).getAll();
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
    
    public void vendorUpdate(String oldKey, Vendor entity) throws Exception {
        int linePosition = indexOfKey(oldKey);
        if(linePosition != -1){
            removeNthLine(this.getFileName(), linePosition);
        }
        this.vendorAdd(entity);
    }
    
    public void vendorAdd(Vendor entity) throws Exception {
        if(indexOfKey(entity.getKey()) != -1){
            throw new Exception("Duplicate Key!");
        }
        try(FileWriter fileWriter = new FileWriter(this.getFileName(), true)){
            fileWriter.append(FileFormatterFactory.getFormatter(RepoType.Vendor, new UnitOfWork()).format(entity));
        }
    }
        
    private int indexOfKey(String key) throws Exception{
        try(FileReader fileReader = new FileReader(this.getFileName())){
            try(BufferedReader bufferedReader = new BufferedReader(fileReader)){
                int linePosition = -1;
                while(bufferedReader.ready()){
                    String lineKey;
                    lineKey = FileFormatterFactory.getFormatter(RepoType.Vendor, new UnitOfWork()).getKey(bufferedReader.readLine());
                    linePosition++;
                    if(key.equals(lineKey)) {
                        return linePosition;
                    }
                }
            }
        }
        return -1;
    }
    
    private static void removeNthLine(String f, int toRemove) throws IOException {
        // Leave the n first lines unchanged.
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            // Leave the n first lines unchanged.
            for (int i = 0; i < toRemove; i++)
                raf.readLine();
            
            long writePos = raf.getFilePointer();
            raf.readLine();
            long readPos = raf.getFilePointer();
            
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = raf.read(buf))) {
                raf.seek(writePos);
                raf.write(buf, 0, n);
                readPos += n;
                writePos += n;
                raf.seek(readPos);
            }
            
            raf.setLength(writePos);
        }
    }
    
}
