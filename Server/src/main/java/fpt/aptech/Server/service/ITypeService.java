/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Type;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ITypeService {
    
    List<Type> findAll();
    
    Type saveType(Type type);
    
    Type findByName(String name);
    
    Type findById(int id);
    
    void delete(int id);
}
