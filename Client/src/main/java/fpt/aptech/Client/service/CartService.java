/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Client.service;

import fpt.aptech.Client.dao.CartDAO;
import fpt.aptech.Client.dto.CartDTO;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CartService implements ICartService{
    @Autowired
    private CartDAO cartDao = new CartDAO();

    @Override
    public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart) {
        return cartDao.AddCart(id, cart);
    }

    @Override
    public HashMap<Integer, CartDTO> EditCart(int id, int quantity, HashMap<Integer, CartDTO> cart) {
        return cartDao.EditCart(id, quantity, cart);
    }

    @Override
    public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart) {
        return cartDao.DeleteCart(id, cart);
    }

    @Override
    public int TotalQuantity(HashMap<Integer, CartDTO> cart) {
        return cartDao.TotalQuantity(cart);
    }

    @Override
    public double TotalPrice(HashMap<Integer, CartDTO> cart) {
        return cartDao.TotalPrice(cart);
    }

    @Override
    public int getProductQuantityInCart(int productId, HashMap<Integer, CartDTO> cart) {
        return cartDao.getProductQuantityInCart(productId, cart);
    }
    
}
