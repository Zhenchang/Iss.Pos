/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.dao.formatters.FileType;

/**
 *
 * @author Liu Zhenchang
 */
public interface IUnitOfWork {
    IRepository getRepository(FileType fileType);
    void addRepository(IRepository repository);
    void removeRepository(IRepository repository);
}
