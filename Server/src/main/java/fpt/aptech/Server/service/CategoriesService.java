/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Categories;
import fpt.aptech.Server.repository.CategoriesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CategoriesService implements ICategories{
    @Autowired
    CategoriesRepository repository;
    

    @Override
    public List<Categories> findAll() {
        return repository.findAll();
    }

    @Override
    public Categories saveCate(Categories categories) {
        return repository.save(categories);
    }

    @Override
    public Categories findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Categories findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
    
}
