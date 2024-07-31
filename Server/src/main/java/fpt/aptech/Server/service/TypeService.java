/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Type;
import fpt.aptech.Server.repository.TypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class TypeService implements ITypeService{

    @Autowired
    TypeRepository repository;
    

    @Override
    public List<Type> findAll() {
        return repository.findAll();
    }

    @Override
    public Type saveType(Type type) {
        return repository.save(type);
    }

    @Override
    public Type findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Type findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
    
}
