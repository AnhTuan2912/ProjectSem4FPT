/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.entities.OrderDetails;
import fpt.aptech.Server.entities.Orders;
import fpt.aptech.Server.service.IOrderDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/orderdetails")
public class OrdersDetailsController {
    @Autowired
     IOrderDetails service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetails> getAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetails> findByOrderId(Orders orderId) {
        return service.findByOrderId(orderId);
    }
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetails create(@RequestBody OrderDetails orderDetails) {
        return service.saveOrderDetails(orderDetails);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
