/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.entities.MonthlySales;
import fpt.aptech.Server.entities.Orders;
import fpt.aptech.Server.repository.OrdersRepository;
import fpt.aptech.Server.service.IOrders;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    IOrders service;
    @Autowired
    OrdersRepository repository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getAll() {
         List<Orders> orders = service.findAll();
        return orders.stream()
                     .sorted(Comparator.comparing(Orders::getOrderDate).reversed())
                     .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Orders findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("/maxid")
    public Integer getMaxId() {
        return repository.findMaxId();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Orders create(@RequestBody Orders orders) {
        return service.saveOrders(orders);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long orderCount = service.countOrders();
        return ResponseEntity.ok(orderCount);
    }

    @GetMapping("/monthly")
    public List<MonthlySales> getMonthlySales() {
        return repository.findMonthlySales();
    }
}
