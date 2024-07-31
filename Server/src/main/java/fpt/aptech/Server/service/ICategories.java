/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Categories;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ICategories {
    List<Categories> findAll();
    
    Categories saveCate(Categories categories);
    
    Categories findByName(String name);
    
    Categories findById(int id);
    
    void delete(int id);
}
