/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.Server.repository;

import fpt.aptech.Server.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
public interface ProductRepository extends JpaRepository<Products, Integer> {
    @Query("SELECT SUM(p.quantity) FROM Products p")
    int findTotalQuantity();
}
