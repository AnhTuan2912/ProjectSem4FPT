/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.PasswordResetRequest;
import fpt.aptech.Server.entities.Users;
import fpt.aptech.Server.repository.PasswordResetRequestRepository;
import fpt.aptech.Server.repository.UsersRepository;
import fpt.aptech.Server.util.EmailUtil;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class UserService implements IUserService {

    @Autowired
    UsersRepository userRepository;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    PasswordResetRequestRepository prrRepository;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users saveUser(Users newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users checkLogin(String email, String pass) {
        Users user = userRepository.checkLogin(email, pass);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public Users findById(int id) {
        return userRepository.findById(id).get();
    }

    public boolean emailExists(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public Users forgotPassword(String email) {
        // Kiểm tra email có tồn tại trong cơ sở dữ liệu hay không
        if (emailExists(email)) {
            String token = UUID.randomUUID().toString();
            PasswordResetRequest request = new PasswordResetRequest(email, token, LocalDateTime.now());
            prrRepository.save(request);
            try {
                emailUtil.sendSetPasswordEmail(email, token);
            } catch (MessagingException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String setPassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email);
        user.setPassword(newPassword);
        userRepository.save(user);
        return "New password set successfully";
    }

    @Override
    public Optional<PasswordResetRequest> findByToken(String token) {
        return prrRepository.findByToken(token);
    }
    

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public long countUser() {
        return userRepository.countUsersByRoleName("Customer");
    }

}
