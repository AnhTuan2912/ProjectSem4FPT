/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Products;
import fpt.aptech.Server.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ProductService implements IProduct{
    @Autowired
    ProductRepository repository; 

    @Override
    public List<Products> findAll() {
        return repository.findAll();
    }

    @Override
    public Products saveProduct(Products product) {
        return repository.save(product);
    }

    @Override
    public Products findById(int id) {
        return repository.findById(id).get();
    }
    
    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public int getTotalQuantity() {
        return repository.findTotalQuantity();
    }
}
