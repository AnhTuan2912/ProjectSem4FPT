/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.PasswordResetRequest;
import fpt.aptech.Server.entities.Users;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
public interface IUserService {
    
    List<Users> findAll();
    
    Users saveUser(Users newUser);
    
    Users findByEmail(String email);
    
    Users checkLogin(String email, String pass);
    
    Users findById(int id);
    
    Users forgotPassword(String email);
    
    String setPassword(String email, String newPassword);
    
    Optional<PasswordResetRequest> findByToken(String token);
    
    void delete(int id);
    
    public long countUser();
}
