/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.dao.format.FileType;

/**
 *
 * @author Liu Zhenchang
 */
public interface IUnitOfWork {
    IRepository getRepository(FileType fileType) throws Exception;
}
