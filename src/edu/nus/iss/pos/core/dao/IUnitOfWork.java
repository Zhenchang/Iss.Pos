/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.core.IEntity;
import edu.nus.iss.pos.dao.format.RepoType;

/**
 *
 * @author Liu Zhenchang
 */
public interface IUnitOfWork {
    IRepository getRepository(RepoType fileType) throws Exception;
    <T extends IEntity> void add(T entity) throws Exception;
    <T extends IEntity> void update(String oldkey, T entity) throws Exception;
    <T extends IEntity> void delete(T entity) throws Exception;
}
