/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.Server.repository;

import fpt.aptech.Server.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Admin
 */
public interface RolesRepository extends JpaRepository<Roles, Integer> {
   
}
