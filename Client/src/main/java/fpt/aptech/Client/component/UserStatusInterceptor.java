/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package fpt.aptech.Client.component;

import fpt.aptech.Client.entities.Users;
import fpt.aptech.Client.enums.Role;
import fpt.aptech.Client.enums.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author Admin
 */
@Component
public class UserStatusInterceptor implements HandlerInterceptor {

    
    RestTemplate rt = new RestTemplate();
    String urlU = "http://localhost:9999/api/users";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users user = (Users) session.getAttribute("user");
            if (user != null) {
                // Kiểm tra trạng thái của người dùng
                Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
                if (currentUser.getStatus() == Status.DEACTIVE.getValue() && currentUser.getRoleId().getId() == Role.CUSTOMER.getValue()) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/user/login?error=Your account has been blocked!");
                    return false;
                }
                if (currentUser.getStatus() == Status.DEACTIVE.getValue() && currentUser.getRoleId().getId() == Role.ADMIN.getValue()) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/admin/login?error=Your account has been blocked!");
                    return false;
                }
            }
        }
        return true;
    }
}
