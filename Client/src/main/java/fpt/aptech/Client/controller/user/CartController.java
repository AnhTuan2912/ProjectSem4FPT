/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.user;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import static fpt.aptech.Client.controller.user.PaymentController.URL_PAYPAL_CANCEL;
import static fpt.aptech.Client.controller.user.PaymentController.URL_PAYPAL_SUCCESS;
import fpt.aptech.Client.dto.CartDTO;
import fpt.aptech.Client.entities.OrderDetails;
import fpt.aptech.Client.entities.Orders;
import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.entities.Users;
import fpt.aptech.Client.enums.PaypalPaymentIntent;
import fpt.aptech.Client.enums.PaypalPaymentMethod;
import fpt.aptech.Client.enums.Status;
import fpt.aptech.Client.service.CartService;
import fpt.aptech.Client.service.PaypalService;
import fpt.aptech.Client.utils.PaypalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@ControllerAdvice
@Controller
@RequestMapping("/user")
public class CartController {

    String urlOD = "http://localhost:9999/api/orderdetails";
    String urlO = "http://localhost:9999/api/orders";
    String urlP = "http://localhost:9999/api/products";
    RestTemplate rt = new RestTemplate();
    @Autowired
    private CartService cartService = new CartService();
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @ModelAttribute
    public void addAttributes(HttpSession session, Model model) {
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart != null) {
            model.addAttribute("TotalQuantityCart", cartService.TotalQuantity(cart));
            model.addAttribute("TotalPriceCart", cartService.TotalPrice(cart));
        } else {
            model.addAttribute("TotalQuantityCart", 0);
            model.addAttribute("TotalPriceCart", 0.0);
        }
    }

    @RequestMapping(value = "AddCart/{id}")
    public ResponseEntity<Map<String, Object>> AddCart(HttpServletRequest request, Model model, HttpSession session, @PathVariable int id) {
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart == null) {
            cart = new HashMap<Integer, CartDTO>();
        }
        Products product = findProductById(id);
        int currentQuantity = cartService.getProductQuantityInCart(id, cart);
        if (currentQuantity + 1 > product.getQuantity()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Not enough stock available. Only " + (product.getQuantity() - currentQuantity) + " items left.");
            return ResponseEntity.ok(response);
        }

        DecimalFormat df = new DecimalFormat("#.00");

        cartService.AddCart(id, cart);
        session.setAttribute("Cart", cart);
        int totalQuantity = cartService.TotalQuantity(cart);
        double totalPrice = cartService.TotalPrice(cart);
        session.setAttribute("TotalQuantityCart", totalQuantity);
        session.setAttribute("TotalPriceCart", df.format(totalPrice));
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("totalQuantity", totalQuantity);
        response.put("totalPrice", df.format(totalPrice));
        response.put("remainingStock", product.getQuantity()- cartService.getProductQuantityInCart(id, cart));
        return ResponseEntity.ok(response);

    }

    @RequestMapping(value = "EditCart/{id}/{quantity}")
    public String EditCart(HttpServletRequest request, HttpSession session, @PathVariable int id, @PathVariable int quantity) {
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart == null) {
            cart = new HashMap<Integer, CartDTO>();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        cart = cartService.EditCart(id, quantity, cart);
        session.setAttribute("Cart", cart);
        session.setAttribute("TotalQuantityCart", cartService.TotalQuantity(cart));
        session.setAttribute("TotalPriceCart", df.format(cartService.TotalPrice(cart)));
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "DeleteCart/{id}")
    public String DeleteCart(HttpServletRequest request, HttpSession session, @PathVariable int id) {
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart == null) {
            cart = new HashMap<Integer, CartDTO>();
        }
        cartService.DeleteCart(id, cart);
        session.setAttribute("Cart", cart);
        session.setAttribute("TotalQuantityCart", cartService.TotalQuantity(cart));
        session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(HttpSession session, Model model) {
        if (session.getAttribute("Cart") == null) {
            return "user/error";
        }
        model.addAttribute("orders", new Orders());
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart == null) {
            cart = new HashMap<Integer, CartDTO>();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        session.setAttribute("Cart", cart);
        session.setAttribute("TotalQuantityCart", cartService.TotalQuantity(cart));
        session.setAttribute("TotalPriceCart", df.format(cartService.TotalPrice(cart)));
        Orders orders = new Orders();
        Users loginInfo = (Users) session.getAttribute("user");
        if (loginInfo != null) {
            Users user = new Users();
            user.getId();
            orders.setName(loginInfo.getName());
            orders.setEmail(loginInfo.getEmail());
            orders.setPhone(loginInfo.getPhone());
            orders.setUserId(user);
        }

        model.addAttribute("orders", orders);
        return "user/checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(HttpSession session, Model model, @ModelAttribute("orders") Orders orders, @RequestParam(value = "price", required = false) Double price, HttpServletRequest request) {
        // Lấy tổng số lượng từ session và thiết lập cho đơn hàng
        Object totalQuantityObj = session.getAttribute("TotalQuantityCart");
        if (totalQuantityObj != null) {
            if (totalQuantityObj instanceof Integer) {
                orders.setTotalQuantity((Integer) totalQuantityObj);
            } else if (totalQuantityObj instanceof String) {
                try {
                    orders.setTotalQuantity(Integer.parseInt((String) totalQuantityObj));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Unsupported type for TotalQuantityCart");
            }
        } else {
            System.out.println("TotalQuantityCart attribute is null");
        }

        // Lấy tổng giá từ session và thiết lập cho đơn hàng
        orders.setTotalPrice(Double.parseDouble((String) session.getAttribute("TotalPriceCart")));
        orders.setOrderDate(LocalDateTime.now());
        orders.setStatus(Status.PENDING.getValue());

        // Lưu thông tin đơn hàng vào session để sử dụng sau khi thanh toán thành công
        session.setAttribute("orders", orders);

        // Kiểm tra phương thức thanh toán và chuyển hướng tương ứng
        String paymentMethod = orders.getPaymentMethod();
        if ("PayPal".equals(paymentMethod)) {
            // Chuyển hướng đến PayPal với thông tin thanh toán
            String cancelUrl = PaypalUtils.getBaseUrl(request) + "/" + URL_PAYPAL_CANCEL;
            String successUrl = PaypalUtils.getBaseUrl(request) + "/" + URL_PAYPAL_SUCCESS;
            try {
                Payment payment = paypalService.createPayment(
                        price != null ? price : orders.getTotalPrice(), // Sử dụng giá trị truyền vào nếu có
                        "USD",
                        PaypalPaymentMethod.paypal,
                        PaypalPaymentIntent.sale,
                        "payment description",
                        cancelUrl,
                        successUrl);
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                log.error(e.getMessage());
            }
            return "redirect:/";
        } else if ("MoMo".equals(paymentMethod)) {

            return "redirect:/payWithMoMo?price=" + orders.getTotalPrice();
        }
        return "user/checkout";
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

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpSession session) {
        binder.registerCustomEditor(Users.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // Lấy đối tượng Users từ session
                Object userObj = session.getAttribute("user");
                if (userObj != null && userObj instanceof Users) {
                    Users user = (Users) userObj;
                    setValue(user);
                } else {
                    // Nếu không tìm thấy user trong session, có thể xử lý tùy ý
                    System.out.println("User object is not found or is not an instance of Users");
                    setValue(null);
                }
            }
        });
    }

}
