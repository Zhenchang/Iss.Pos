/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.repositories;

import edu.nus.iss.pos.core.*;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.FileType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zz
 */
public class UnitOfWork implements IUnitOfWork {

    private final List<IRepository> repositories;
    public UnitOfWork() throws Exception{
        repositories = new ArrayList();
        repositories.add(new Repository<>(this, FileType.User, "Storekeepers.dat"));
        repositories.add(new Repository<>(this, FileType.Member, "Members.dat"));
        repositories.add(new Repository<>(this, FileType.Category, "Category.dat"));
        repositories.add(new Repository<>(this, FileType.Product, "Products.dat"));
        repositories.add(new Repository<>(this, FileType.Transaction, "Transactions.dat"));
        repositories.add(new Repository<>(this, FileType.Discount, "Discounts.dat"));
        repositories.add(new VendorRepository(this, "Vendors"));
    }
    
    @Override
    public IRepository getRepository(FileType fileType) throws Exception {
        for(IRepository r : repositories){
            if(r.getFileType().equals(fileType)){
                return r;
            }
        }
        throw new Exception("No Such Repository!");
    }

    @Override
    public <T extends IEntity> void add(T entity) throws Exception {
        IRepository repo = getRepository(entity);
        repo.add(entity);
    }

    @Override
    public <T extends IEntity> void update(String oldkey, T entity) throws Exception {
        IRepository repo = getRepository(entity);
        repo.update(oldkey, entity);
    }

    @Override
    public <T extends IEntity> void delete(T entity) throws Exception {
        IRepository repo = getRepository(entity);
        repo.delete(entity);
    }
    
    private IRepository getRepository(IEntity entity) throws Exception{
        if(entity instanceof User) getRepository(FileType.User);
        if(entity instanceof Member) getRepository(FileType.Member);
        if(entity instanceof Category) getRepository(FileType.Category);
        if(entity instanceof Product) getRepository(FileType.Product);
        if(entity instanceof Transaction) getRepository(FileType.Transaction);
        if(entity instanceof Discount) getRepository(FileType.Discount);
        if(entity instanceof Vendor) getRepository(FileType.Vendor);
        throw new Exception("No Such Repository!");
    }
}
