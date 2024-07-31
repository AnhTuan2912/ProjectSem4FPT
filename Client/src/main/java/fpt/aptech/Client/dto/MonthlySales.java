/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dto;

/**
 *
 * @author Admin
 */
public class MonthlySales {
    private String month;
    private double total;

    public MonthlySales(String month, double total) {
        this.month = month;
        this.total = total;
    }

    public MonthlySales() {
    }
    
    

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
