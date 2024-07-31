/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.Server.repository;

import fpt.aptech.Server.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Admin
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    
    @Query("SELECT u FROM Users u WHERE u.email = :email and u.password = :password")
    Users checkLogin(@PathVariable("email") String email, @PathVariable("password") String password);
    
    Users findByEmail(String email);
    
     @Query("SELECT COUNT(u) FROM Users u WHERE u.roleId.name = :roleName")
    long countUsersByRoleName(@Param("roleName") String roleName);
}
