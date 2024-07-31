/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.Orders;
import fpt.aptech.Server.repository.OrderDetailsRepository;
import fpt.aptech.Server.repository.OrdersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class OrdersService implements IOrders{
    @Autowired
    OrdersRepository repository;

    
    @Override
    public List<Orders> findAll() {
        return repository.findAll();
    }

    @Override
    public Orders saveOrders(Orders orders) {
        return repository.save(orders);
    }
    
    @Override
    public void delete(int id) {

        repository.deleteById(id);
        
    }

    @Override
    public Orders findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public long countOrders() {
        return repository.count();
    }
//    @Override
//    public List<Object[]> getMonthlySales() {
//        return repository.findMonthlySales();
//    }
}
