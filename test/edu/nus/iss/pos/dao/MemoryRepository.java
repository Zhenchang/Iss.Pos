/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao;

import edu.nus.iss.pos.core.IEntity;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.RepoType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zz
 * @param <T>
 */

public class MemoryRepository<T extends IEntity> implements IRepository<T> {

    private RepoType fileType;
    private String fileName;
    private final IUnitOfWork unitOfWork;
    private List<T> list;

    public MemoryRepository(IUnitOfWork unitOfWork, RepoType fileType, String filename) throws Exception {
        this.setFileType(fileType);
        this.unitOfWork = unitOfWork;
        list = new ArrayList();
    }

    private void setFileType(RepoType fileType) {
        this.fileType = fileType;
    }

    @Override
    public void add(T entity) throws Exception {
        if (indexOfKey(entity.getKey()) != -1) {
            throw new Exception("Duplicate Key!");
        }
        list.add(entity);
    }

    private int indexOfKey(String key) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (t.getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void delete(T entity) throws Exception {
        int pos = indexOfKey(entity.getKey());
        if (pos != -1) {
            list.remove(pos);
        }
    }

    @Override
    public void update(String oldKey, T entity) throws Exception {
        int pos = indexOfKey(oldKey);
        if (pos != -1) {
            list.remove(pos);
        }
        this.add(entity);
    }

    @Override
    public T getByKey(String key) throws Exception {
        int pos = indexOfKey(key);
        if(pos == -1){
            return null;
        }
        return list.get(pos);

    }

    @Override
    public Iterable<T> getAll() throws Exception {
        return list;
    }

    @Override
    public RepoType getFileType() {
        return this.fileType;
    }

    @Override
    public IUnitOfWork getUnitOfWork() {
        return this.unitOfWork;
    }
}
