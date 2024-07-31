/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.entities.Categories;
import fpt.aptech.Server.service.ICategories;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    ICategories service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Categories> get() {
        return service.findAll();
    }
    
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Categories getOne(@PathVariable int id) {
        return service.findById(id);
    }
    
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Categories getName(@PathVariable String name) {
        return service.findByName(name);
    }
    

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Categories post(@RequestBody Categories categories) {
        return service.saveCate(categories);
    }
    
    
    
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public Categories put(@RequestBody Categories categories) {
        return service.saveCate(categories);
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
    
    
    
    
}
