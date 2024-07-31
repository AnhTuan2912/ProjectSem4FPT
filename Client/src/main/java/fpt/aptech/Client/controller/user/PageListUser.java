/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.user;

import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.entities.Users;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/user")
public class PageListUser {
    String urlU = "http://localhost:9999/api/users";
    String urlC = "http://localhost:9999/api/categories";
    String urlP = "http://localhost:9999/api/products";
    RestTemplate rt = new RestTemplate();
    
    @RequestMapping("/index")
    public String index(Model model,HttpSession session) {
         Users user = (Users) session.getAttribute("user");
        List<Products> bList = rt.getForObject(urlP + "/" , List.class);
        model.addAttribute("list",bList);
        rt.postForEntity("http://localhost:9999/api/visit/increment", null, int.class);
        if (user != null) {
            model.addAttribute("username", user.getName());
        }
        return "user/index";
        
    }
    
    
    @RequestMapping("/products")
    public String products(Model model) {
        List<Products> bList = rt.getForObject(urlP + "/" , List.class);
        model.addAttribute("list",bList);
        return "user/products";
    }
    
   
    
    
    @RequestMapping("/blogs")
    public String blogs(Model model) {
        
        return "user/blogs";
    }
    
    @RequestMapping("/about")
    public String about(Model model) {
        
        return "user/about";
    }
    
    @RequestMapping("/contact")
    public String contact(Model model) {
        
        return "user/contact";
    }
    
    @RequestMapping("/cart")
    public String cart(Model model) {
        
        return "user/cart";
    }
    
    @RequestMapping("/single/{id}")
    public String single(Model model,@PathVariable int id) {
        Products product = rt.getForObject(urlP + "/" + id, Products.class);
        model.addAttribute("product", product);
        return "user/single";
    }
    
    
}
