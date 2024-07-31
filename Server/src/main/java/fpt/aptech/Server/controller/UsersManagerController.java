/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.dto.PasswordResetRequestDTO;
import fpt.aptech.Server.entities.PasswordResetRequest;
import fpt.aptech.Server.entities.Users;
import fpt.aptech.Server.repository.PasswordResetRequestRepository;
import fpt.aptech.Server.service.IUserService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/users")
public class UsersManagerController {

    @Autowired
    IUserService userService;
    @Autowired
    private PasswordResetRequestRepository passwordResetRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Users> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users getOne(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Users findEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Users post(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public Users put(@RequestBody Users user) {
        return userService.saveUser(user);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @PostMapping("/{email}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public Users checkLogin(@PathVariable String email, @PathVariable String password) {
        return userService.checkLogin(email, password);
    }

    @PostMapping("/forgot-password/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Users forgotPassword(@PathVariable String email) {
        return userService.forgotPassword(email);
          
    }

    @PostMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestBody PasswordResetRequestDTO requestDTO) {
        String token = requestDTO.getToken();
        String newPassword = requestDTO.getNewPassword();
        
        Optional<PasswordResetRequest> optionalRequest = passwordResetRepository.findByToken(token);
        if (optionalRequest.isPresent()) {
            PasswordResetRequest request = optionalRequest.get();
            LocalDateTime requestTime = request.getRequestTime();
            LocalDateTime now = LocalDateTime.now();
            long minutes = ChronoUnit.MINUTES.between(requestTime, now);

            if (minutes <= 30) { // Thời gian timeout là 60 phút
                Users user = userService.findByEmail(request.getEmail());
                if (user != null) {
                    // Mã hóa mật khẩu trước khi lưu
                    user.setPassword(newPassword);
                    userService.saveUser(user);
                    passwordResetRepository.delete(request);
                    return ResponseEntity.ok("Password has been successfully reset.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        }
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<PasswordResetRequest> getToken(@PathVariable String token){
        Optional<PasswordResetRequest> optionalRequest = passwordResetRepository.findByToken(token);
        return ResponseEntity.of(optionalRequest);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long userCount = userService.countUser();
        return ResponseEntity.ok(userCount);
    }
}
