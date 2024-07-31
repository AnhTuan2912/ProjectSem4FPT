/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.OrderDetails;
import fpt.aptech.Server.entities.Orders;
import fpt.aptech.Server.repository.OrderDetailsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class OrderDetailsService implements IOrderDetails{
    @Autowired
    OrderDetailsRepository repository;
    
    @Override
    public List<OrderDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public OrderDetails saveOrderDetails(OrderDetails orderDetails) {
        return repository.save(orderDetails);
    }

    @Override
    public void delete(int id) {
       repository.deleteById(id);
    }

    @Override
    public List<OrderDetails> findByOrderId(Orders orderId) {
        return repository.findByOrderId(orderId);
    }

   
    
}
