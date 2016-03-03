/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.core.IEntity;
import java.util.Collection;

/**
 *
 * @author Liu Zhenchang
 */
interface IRepository<T extends IEntity> {
    
     void add(T entity);
     void delete(T entity);
     void update(T entity);
     T getByKey(String key);
     Collection<T> getAll();
}
