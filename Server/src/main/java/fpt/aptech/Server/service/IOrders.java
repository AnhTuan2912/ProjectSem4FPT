/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Orders;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IOrders {
    List<Orders> findAll();
    
    Orders saveOrders(Orders orders);
    
    void delete(int id);
    
    Orders findById(int id);
    
    long countOrders();
    
//    public List<Object[]> getMonthlySales();
        
}
