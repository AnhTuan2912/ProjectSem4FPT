/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.OrderDetails;
import fpt.aptech.Server.entities.Orders;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IOrderDetails {
    List<OrderDetails> findAll();
    
    OrderDetails saveOrderDetails(OrderDetails orderDetails);
    
    public void delete(int id);
    
    List<OrderDetails> findByOrderId(Orders orderId);
        
    
}
