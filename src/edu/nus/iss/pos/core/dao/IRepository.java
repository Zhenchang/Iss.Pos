/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.core.IEntity;
import edu.nus.iss.pos.dao.formatters.FileType;
import java.io.IOException;

/**
 *
 * @author Liu Zhenchang
 */
public interface IRepository<T extends IEntity> {
    
     void add(T entity) throws Exception;
     void delete(T entity) throws Exception;
     void update(String oldKey, T entity) throws Exception;
     
     T getByKey(String key) throws Exception;
     Iterable<T> getAll() throws Exception;
     FileType getFileType();
     IUnitOfWork getUnitOfWork();
     void setFileName(String fileName) throws IOException;
             
}
