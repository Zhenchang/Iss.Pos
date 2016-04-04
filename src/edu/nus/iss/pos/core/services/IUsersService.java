/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.core.services;

import edu.nus.iss.pos.core.User;
import java.util.List;



/**
 *
 * @author Liu Zhenchang
 */
public interface IUsersService {
    User Login (String username, String password) throws Exception;
    User getUserByName(String username) throws Exception;
    void ChangePassword(String username, String oldPassword, String newPassword) throws Exception;
    User addUser(String username, String password) throws Exception;
    void deleteUser(String username) throws Exception;
    boolean checkPasswordComplexity(String password) throws Exception;
    List<User> getAllUser() throws Exception;
}
