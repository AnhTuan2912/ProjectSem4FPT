/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dto;

import fpt.aptech.Client.entities.Products;

/**
 *
 * @author Admin
 */
public class CartDTO {
    private int quantity;
    private double totalPrice;
    private Products product;
    
    
    

    public CartDTO() {
    }

    public CartDTO(int quantity, double totalPrice, Products product) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.product = product;
    }
    
    
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
    
    
    
}
