/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.repositories;

import edu.nus.iss.pos.core.*;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.RepoType;
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
        repositories.add(new Repository<>(this, RepoType.User, "data/Storekeepers.dat"));
        repositories.add(new Repository<>(this, RepoType.Member, "data/Members.dat"));
        repositories.add(new Repository<>(this, RepoType.Category, "data/Category.dat"));
        repositories.add(new Repository<>(this, RepoType.Product, "data/Products.dat"));
        repositories.add(new Repository<>(this, RepoType.Transaction, "data/Transactions.dat"));
        repositories.add(new Repository<>(this, RepoType.Discount, "data/Discounts.dat"));
        repositories.add(new VendorRepository(this, "Vendors"));
    }
    
    @Override
    public IRepository getRepository(RepoType fileType) throws Exception {
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
        if(entity instanceof User) return getRepository(RepoType.User);
        if(entity instanceof Member) return  getRepository(RepoType.Member);
        if(entity instanceof Category) return  getRepository(RepoType.Category);
        if(entity instanceof Product) return getRepository(RepoType.Product);
        if(entity instanceof Transaction) return  getRepository(RepoType.Transaction);
        if(entity instanceof Discount) return getRepository(RepoType.Discount);
        if(entity instanceof Vendor)  return getRepository(RepoType.Vendor);
        throw new Exception("No Such Repository!");
    }
}
