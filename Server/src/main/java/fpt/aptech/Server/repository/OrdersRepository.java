/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.Server.repository;

import fpt.aptech.Server.entities.MonthlySales;
import fpt.aptech.Server.entities.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT COALESCE(MAX(o.id), 0) FROM Orders o")
    Integer findMaxId();

//    @Query("SELECT FUNCTION('MONTH', o.orderDate) AS month, SUM(o.totalQuantity) AS totalQuantity "
//            + "FROM Orders o "
//            + "WHERE FUNCTION('YEAR', o.orderDate) = FUNCTION('YEAR', CURRENT_DATE) "
//            + "GROUP BY FUNCTION('MONTH', o.orderDate)")
//    List<Object[]> findMonthlySales();
    

    @Query("SELECT new fpt.aptech.Server.entities.MonthlySales(FORMAT(o.orderDate, 'yyyy-MM'), SUM(o.totalQuantity)) " +
           "FROM Orders o GROUP BY FORMAT(o.orderDate, 'yyyy-MM')")
    List<MonthlySales> findMonthlySales();
}
