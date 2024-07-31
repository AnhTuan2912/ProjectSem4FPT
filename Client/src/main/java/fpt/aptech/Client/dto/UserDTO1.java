/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dto;

import fpt.aptech.Client.entities.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public class UserDTO1 {
     private Integer id;

    
    private String name;

  
    private String email;

  
    private String password;

    
    private String confirmPassword;
    
    private String phone;

    private String address;

    private MultipartFile avatar;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer status;

    private Roles roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public UserDTO1() {
    }

    public UserDTO1(Integer id, String name, String email, String password, String phone, String address, MultipartFile avatar, LocalDateTime created, LocalDateTime updated, Integer status, Roles roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.roleId = roleId;
    }
    
    
    
}
