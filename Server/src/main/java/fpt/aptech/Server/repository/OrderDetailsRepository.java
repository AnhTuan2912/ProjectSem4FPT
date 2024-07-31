/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.Server.repository;

import fpt.aptech.Server.entities.OrderDetails;
import fpt.aptech.Server.entities.Orders;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author Admin
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
     @Query("SELECT od FROM OrderDetails od WHERE od.orderId = :orderId")
    List<OrderDetails> findByOrderId(@PathVariable("orderId") Orders orderId);
    

}
