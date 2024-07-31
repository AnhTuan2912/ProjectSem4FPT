/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dto;

import fpt.aptech.Client.entities.Roles;
import fpt.aptech.Client.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Admin
 */
@Getter
@Setter
public class UserDTO {

    private Integer id;

    @NotNull(message = "Name  is required...")
    @NotBlank(message = "Name  is required...")
    private String name;

    @NotNull(message = "Email  is required...")
    @NotBlank(message = "Email  is required...")
    private String email;

    @NotNull(message = "Password  is required...")
    @NotBlank(message = "Password  is required...")
    private String password;

    @NotNull(message = "confirmPassword  is required...")
    @NotBlank(message = "confirmPassword  is required...")
    private String confirmPassword;
    
    private String phone;

    private String address;

    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
     @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

   
    
    
    
}
