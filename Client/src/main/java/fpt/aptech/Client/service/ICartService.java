/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.Client.service;

import fpt.aptech.Client.dto.CartDTO;
import java.util.HashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public interface ICartService {
    public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart);
    public HashMap<Integer, CartDTO> EditCart(int id, int quantity, HashMap<Integer, CartDTO> cart);
    public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart);
    public int TotalQuantity(HashMap<Integer, CartDTO> cart);
    public double TotalPrice(HashMap<Integer, CartDTO> cart);
    public int getProductQuantityInCart(int productId, HashMap<Integer, CartDTO> cart);
}
