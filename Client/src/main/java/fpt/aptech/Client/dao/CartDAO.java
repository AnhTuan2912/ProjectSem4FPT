/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dao;

import fpt.aptech.Client.dto.CartDTO;
import fpt.aptech.Client.dto.ProductDTO;
import fpt.aptech.Client.entities.Products;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Repository
public class CartDAO {

    String urlP = "http://localhost:9999/api/products";
    RestTemplate rt = new RestTemplate();

    public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart) {
        CartDTO itemCart = new CartDTO();
        Products product = findProductById(id);
        int stockQuantity = product.getQuantity(); // Giả sử phương thức này trả về số lượng tồn kho
        int currentCartQuantity = cart.getOrDefault(id, new CartDTO()).getQuantity();

        // Kiểm tra xem số lượng sản phẩm trong giỏ hàng có vượt quá số lượng tồn kho hay không
        if (currentCartQuantity + 1 > stockQuantity) {
            return cart; // Không thêm sản phẩm vào giỏ hàng nếu vượt quá số lượng tồn kho
        }
        if (product != null && cart.containsKey(id)) {
            itemCart = cart.get(id);
            itemCart.setQuantity(itemCart.getQuantity() + 1);
            double afterDiscount = itemCart.getProduct().getPrice() - ((product.getPrice() * product.getDiscount()) / 100);
            itemCart.setTotalPrice(itemCart.getQuantity() * afterDiscount);
        } else {
            itemCart.setProduct(product);
            itemCart.setQuantity(1);
            itemCart.setTotalPrice(product.getPrice() - ((product.getPrice() * product.getDiscount()) / 100));
        }
        cart.put(id, itemCart);
        return cart;

    }

    public HashMap<Integer, CartDTO> EditCart(int id, int quantity, HashMap<Integer, CartDTO> cart) {
        if (cart == null || !cart.containsKey(id)) {
            return cart;
        }

        CartDTO itemCart = cart.get(id);
        if (itemCart != null) {
            itemCart.setQuantity(quantity);

            // Tính tổng giá sau khi chiết khấu
            double afterDiscount = itemCart.getProduct().getPrice() - ((itemCart.getProduct().getPrice() * itemCart.getProduct().getDiscount()) / 100);
            double totalPrice = quantity * afterDiscount;

            itemCart.setTotalPrice(totalPrice);
            cart.put(id, itemCart);
        }

        return cart;
    }

    public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart) {
        if (cart == null) {
            return cart;
        }
        if (cart.containsKey(id)) {
            cart.remove(id);
        }
        return cart;

    }

    public int TotalQuantity(HashMap<Integer, CartDTO> cart) {
        int totalQuantity = 0;
        for (Map.Entry<Integer, CartDTO> itemCart : cart.entrySet()) {
            totalQuantity += itemCart.getValue().getQuantity();
        }
        return totalQuantity;
    }

    public double TotalPrice(HashMap<Integer, CartDTO> cart) {
        double totalPrice = 0;
        for (Map.Entry<Integer, CartDTO> itemCart : cart.entrySet()) {
            totalPrice += itemCart.getValue().getTotalPrice();
        }
        return totalPrice;
    }

    public int getProductQuantityInCart(int productId, HashMap<Integer, CartDTO> cart) {
        CartDTO cartDTO = cart.get(productId);
        return (cartDTO != null) ? cartDTO.getQuantity() : 0;
    }

    private Products findProductById(int id) {
        try {
            ResponseEntity<Products> response = rt.getForEntity(urlP + "/" + id, Products.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e; // Ném ngoại lệ nếu gặp lỗi khác
        }
    }
}
