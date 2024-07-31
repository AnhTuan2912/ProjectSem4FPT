/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Roles;
import fpt.aptech.Server.repository.RolesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class RoleService {
    @Autowired
    RolesRepository repository;
    
    public List<Roles> findAll(){
        return repository.findAll();
    }
    
    public Roles findById(int id){
        return repository.findById(id).get();
    }
}
