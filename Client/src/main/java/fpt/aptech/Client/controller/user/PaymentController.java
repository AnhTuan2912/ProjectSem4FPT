/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import fpt.aptech.Client.dto.CartDTO;
import fpt.aptech.Client.entities.OrderDetails;
import fpt.aptech.Client.entities.Orders;
import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.enums.PaypalPaymentIntent;
import fpt.aptech.Client.enums.PaypalPaymentMethod;
import fpt.aptech.Client.service.PaypalService;
import fpt.aptech.Client.utils.PaypalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@RequestMapping("/user")
@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "user/success";
    public static final String URL_PAYPAL_CANCEL = "user/cancel";
    String urlOD = "http://localhost:9999/api/orderdetails";
    String urlO = "http://localhost:9999/api/orders";
    String urlP = "http://localhost:9999/api/products";
    @Autowired
    private APIContext apiContext;
    RestTemplate rt = new RestTemplate();
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/testpp")
    public String index() {
        return "user/testpp";
    }

    @GetMapping("/success1")
    public String success() {
        return "user/success";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        // Chỉ hiển thị trang hủy mà không cần tham số
        return "user/cancel";
    }

    @GetMapping("/success")
    public String paypalSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session) {
        try {
            // Lấy thông tin thanh toán từ PayPal
            Payment payment = paypalService.getPaymentDetails(paymentId);
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);
            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            if (executedPayment.getState().equals("approved")) {
                // Thanh toán thành công, lưu thông tin đơn hàng
                Orders orders = (Orders) session.getAttribute("orders");
                if (orders != null) {
                    Orders createOrder = rt.postForObject(urlO + "/create", orders, Orders.class);
                    // Xử lý chi tiết đơn hàng
                    HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
                    if (cart != null) {
                        for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
                            Integer productId = entry.getKey();
                            CartDTO cartItem = entry.getValue();
                            int quantityOrdered = cartItem.getQuantity();

                            // Cập nhật số lượng sản phẩm
                            Products product = rt.getForObject(urlP + "/" + productId, Products.class);
                            if (product != null) {
                                int updatedQuantity = product.getQuantity() - quantityOrdered;
                                product.setQuantity(updatedQuantity);
                                rt.put(urlP + "/edit", product);
                            }
                        }
                        AddOrderDetails(cart);
                    }
                    // Xóa thông tin liên quan đến giỏ hàng sau khi lưu đơn hàng
                    session.removeAttribute("Cart");
                    session.removeAttribute("TotalQuantityCart");
                    session.removeAttribute("TotalPriceCart");
                    session.removeAttribute("orders");
                    return "user/success"; // Hoặc trang cảm ơn
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/checkout"; // Hoặc trang lỗi
    }

    public void AddOrderDetails(HashMap<Integer, CartDTO> carts) {
        Orders idOrders = GetIdLastOrders();
        for (Map.Entry<Integer, CartDTO> itemCart : carts.entrySet()) {
            int productId = itemCart.getValue().getProduct().getId();
            Products product = new Products();
            product.setId(productId);
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(idOrders);
            orderDetails.setProductId(product);
            orderDetails.setQuantity(itemCart.getValue().getQuantity());
            orderDetails.setTotalPrice(itemCart.getValue().getTotalPrice());
            rt.postForEntity(urlOD + "/create", orderDetails, OrderDetails.class);
        }
    }

    public Orders GetIdLastOrders() {
        Orders id = rt.getForObject(urlO + "/maxid", Orders.class);
        return id;
    }
}
