/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.dto.ProductSalesDTO;
import fpt.aptech.Server.entities.Products;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IProduct {
    List<Products> findAll();
    
    Products findById(int id);
    
    Products saveProduct(Products product);
    
    void delete(int id);
    
    public int getTotalQuantity();

}
