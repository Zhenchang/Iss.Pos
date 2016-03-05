/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.services;

import edu.nus.iss.pos.core.User;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.core.services.IUsersService;
import edu.nus.iss.pos.dao.format.FileType;

/**
 *
 * @author Zaid
 */
public class UsersService implements IUsersService {

    private final IUnitOfWork unitOfWork;
    
    public UsersService(IUnitOfWork unitOfWork){
        if(unitOfWork == null) throw new IllegalArgumentException("unitOfWork");
        this.unitOfWork = unitOfWork;
    }
    
    @Override
    public User Login(String username, String password) throws Exception {
        User u = getUserByName(username);
        if(u != null && u.getPassword().equals(password)){
            return u;
        }
        throw new Exception("Invalid username or password");
    }

    @Override
    public void ChangePassword(String username, String oldPassword, String newPassword) throws Exception {
        User u = Login(username, oldPassword);
        u.setPassword(newPassword);
        unitOfWork.update(username, u);
    }

    @Override
    public User addUser(String username, String password) throws Exception {
        if(!checkPasswordComplexity(password)) throw new Exception ("Password should be 4 characters long");
        User u = new User(username, password);
        unitOfWork.add(u);
        return u;
    }

    @Override
    public void deleteUser(String username) throws Exception {
        User u = getUserByName(username);
        unitOfWork.delete(u);
    }

    @Override
    public User getUserByName(String username) throws Exception {
        return (User) unitOfWork.getRepository(FileType.User).getByKey(username);
    }  

    @Override
    public boolean checkPasswordComplexity(String password) throws Exception {
        if(password.length() < 4) return false;
        return true;
    }
}
