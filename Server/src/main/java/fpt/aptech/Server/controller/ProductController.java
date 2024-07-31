/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.entities.Products;
import fpt.aptech.Server.service.IProduct;
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
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProduct service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Products> get() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Products getOne(@PathVariable int id) {
        return service.findById(id);
    }
    
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public int getAllQuantityProduct() {
        return service.getTotalQuantity();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Products post(@RequestBody Products product) {
        return service.saveProduct(product);
    }
    
    
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public Products put(@RequestBody Products product) {
        return service.saveProduct(product);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
