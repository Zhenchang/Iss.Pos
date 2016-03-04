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
    
}
