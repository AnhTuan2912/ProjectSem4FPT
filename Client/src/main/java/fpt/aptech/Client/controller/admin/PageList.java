/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.aptech.Client.dto.MonthlySales;
import fpt.aptech.Client.entities.Categories;
import fpt.aptech.Client.entities.Orders;
import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.entities.Type;
import fpt.aptech.Client.entities.Users;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/admin")
public class PageList {

    String urlU = "http://localhost:9999/api/users";
    String urlC = "http://localhost:9999/api/categories";
    String urlT = "http://localhost:9999/api/type";
    String urlP = "http://localhost:9999/api/products";
    String urlO = "http://localhost:9999/api/orders";
    RestTemplate rt = new RestTemplate();

    @RequestMapping("/dashboard")
    public String page(HttpSession session, Model model) {
        // Kiểm tra nếu session là null hoặc không có thông tin người dùng
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/admin/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }
        ResponseEntity<Long> oResponse = rt.getForEntity(urlO + "/count", Long.class);
        Long orderCount = oResponse.getBody();
        model.addAttribute("ordersCount", orderCount);
        
        ResponseEntity<Long> pResponse = rt.getForEntity(urlP + "/count", Long.class);
        Long productCount = pResponse.getBody();
        model.addAttribute("productCount", productCount);

        ResponseEntity<Long> uResponse = rt.getForEntity(urlU + "/count", Long.class);
        Long userCount = uResponse.getBody();
        model.addAttribute("usersCount", userCount);

        ResponseEntity<Integer> visitCountResponse = rt.getForEntity("http://localhost:9999/api/visit/count", Integer.class);
        Integer visitCount = visitCountResponse.getBody();
        model.addAttribute("visitCount", visitCount);

        ResponseEntity<List<MonthlySales>> salesResponse = rt.exchange(
                "http://localhost:9999/api/orders/monthly",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MonthlySales>>() {
        }
        );
        List<MonthlySales> monthlySales = salesResponse.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String monthlySalesJson = objectMapper.writeValueAsString(monthlySales);
            model.addAttribute("monthlySalesJson", monthlySalesJson);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có, ví dụ: ghi log hoặc thêm thông báo lỗi vào model
        }

        return "admin/dashboard";
    }

    @RequestMapping("/staff")
    public String listStaff(Model model) {
        List<Users> bList = rt.getForObject(urlU + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/staff";
    }

    @RequestMapping("/categories")
    public String listCate(Model model) {
        List<Categories> bList = rt.getForObject(urlC + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/categories";
    }

    @RequestMapping("/types")
    public String listType(Model model) {
        List<Type> bList = rt.getForObject(urlT + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/types";
    }

    @RequestMapping("/products")
    public String listPro(Model model) {
        List<Products> bList = rt.getForObject(urlP + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/products";
    }

    @RequestMapping("/customer")
    public String listCustomers(Model model, HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/admin/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }
        List<Users> bList = rt.getForObject(urlU + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/customer";
    }

    @RequestMapping("/orders")
    public String listOrder(Model model, HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/admin/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }
        List<Orders> bList = rt.getForObject(urlO + "/", List.class);
        model.addAttribute("list", bList);
        return "admin/orders";
    }

}
