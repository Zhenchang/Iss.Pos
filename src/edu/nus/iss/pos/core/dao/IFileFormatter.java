/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.dao;

import edu.nus.iss.pos.core.IEntity;

/**
 *
 * @author Liu Zhenchang
 */
public interface IFileFormatter<T extends IEntity> {
    String format(T entity);
}
