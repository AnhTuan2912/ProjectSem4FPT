/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.entities.Roles;
import fpt.aptech.Server.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    RoleService service;
    
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Roles> get() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Roles getOne(@PathVariable int id) {
        return service.findById(id);
    }
    
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void delete(@PathVariable int id) {
//        service.delete(id);
//    }
}
