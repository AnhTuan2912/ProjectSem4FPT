/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.user;


import fpt.aptech.Client.dto.PasswordResetRequestDTO;
import fpt.aptech.Client.dto.UserDTO;
import fpt.aptech.Client.dto.UserDTO1;
import fpt.aptech.Client.entities.OrderDetails;
import fpt.aptech.Client.entities.Orders;
import fpt.aptech.Client.entities.PasswordResetRequest;
import fpt.aptech.Client.entities.Roles;
import fpt.aptech.Client.entities.Users;
import fpt.aptech.Client.enums.Role;
import fpt.aptech.Client.enums.Status;



import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    RestTemplate restTemplate;
    String urlU = "http://localhost:9999/api/users";
    String urlOD = "http://localhost:9999/api/orderdetails";
    String urlO = "http://localhost:9999/api/orders";
    RestTemplate rt = new RestTemplate();
    @Value("${upload.path}")
    private String fileUpload;

    @RequestMapping("/login")
    public String showLogin(HttpSession session) {

        return "user/login";
    }

    @PostMapping("/checkLogin")
    public String checkLogin(@RequestParam("email") @NotBlank String email, @RequestParam("password") @NotBlank String password, ModelMap model, HttpSession session) {

        try {
            ResponseEntity<Users> response = rt.postForEntity(urlU + "/" + email + "/" + password, null, Users.class, email, password);

            if (response.getStatusCode() == HttpStatus.OK) {
                Users user = response.getBody();
                session.setAttribute("user", user);
                model.addAttribute("error", "Invalid email or password");
                model.addAttribute("username", user.getName());
                if (user.getRoleId().getId() == Role.ADMIN.getValue()) {
                    session.invalidate();
                    return "user/login";
                }
                if (user.getStatus() == Status.DEACTIVE.getValue()) {
                    session.invalidate();
                    model.addAttribute("error", "Your account has been blocked!");
                    return "user/login";
                }
                return "redirect:/user/index";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/user/index";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") UserDTO user,
            BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                result.rejectValue("confirmPassword", null, "confirmPassword must match");
            }
            return "user/register";
        }
        // Kiểm tra xem Email đã tồn tại hay chưa
        Users emailExists = checkIfEmailExists(user.getEmail());
        if (emailExists != null) {
            result.rejectValue("email", null, "Email already exits!");
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                result.rejectValue("confirmPassword", null, "confirmPassword must match");
            }
            return "user/register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "confirmPassword must match");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/register";
        }

        Roles role = new Roles();
        role.setId(Role.CUSTOMER.getValue());
        user.setRoleId(role);
        Users u = new Users(user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), user.getPhone(), user.getAddress(), user.getAvatar(), LocalDateTime.now(), LocalDateTime.now(), Status.ACTIVE.getValue(), role);
        // Tạo danh mục mới
        Users createUser = rt.postForEntity(urlU + "/create", u, Users.class).getBody();
        model.addAttribute("user", createUser);
        ra.addFlashAttribute("message", "Success! your registration is now complete");
        return "redirect:/user/login";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword() {

        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model, RedirectAttributes ra, UserDTO user) {
        Users emailExists = checkIfEmailExists(user.getEmail());
        if (emailExists == null) {
            model.addAttribute("error", "Not found Email!");
            return "user/forgot-password";
        } else {
            rt.postForEntity(urlU + "/forgot-password/" + email, null, Users.class, email);
            model.addAttribute("message", "SendMail complete, please check your mail!");
            return "user/forgot-password";
        }

    }

    @GetMapping("/set-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("token", token);
        try {
            ResponseEntity<Optional<PasswordResetRequest>> response = restTemplate.exchange(
                    "http://localhost:9999/api/users/token/" + token,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Optional<PasswordResetRequest>>() {
            },
                    uriVariables
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isPresent()) {
                PasswordResetRequest request = response.getBody().get();
                LocalDateTime requestTime = request.getRequestTime();
                LocalDateTime now = LocalDateTime.now();
                long minutes = ChronoUnit.MINUTES.between(requestTime, now);

                if (minutes <= 30) { // Thời gian timeout là 30 phút
                    model.addAttribute("token", token);
                    return "user/set-password";
                } else {
                    return "user/error"; // Chuyển hướng đến trang lỗi nếu token đã hết hạn
                }

            }
        } catch (Exception ex) {
            return "user/error";
        }
        return "user/error";
    }

    @PostMapping("/set-password")
    public String handleResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorP", "New Password and Confirm Password do not match.");
            model.addAttribute("token", token);
            return "user/set-password"; // Trả về trang thay đổi mật khẩu với thông báo lỗi
        }
        PasswordResetRequestDTO requestDTO = new PasswordResetRequestDTO();
        requestDTO.setToken(token);
        requestDTO.setNewPassword(newPassword);

        // Gọi phương thức setPassword từ Rest Controller
        try {
            // Gọi phương thức setPassword từ Rest Controller
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9999/api/users/set-password", requestDTO, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("message", "Password has been successfully reset.");
                return "user/login";
            } else {
                model.addAttribute("error", "Token Invalid");
                return "user/set-password";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST || e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                model.addAttribute("error", "Token Invalid or Request Error");
                return "user/set-password";
            } else {
                model.addAttribute("error", "An error occurred while processing your request.");
                return "user/set-password";
            }
        } catch (Exception e) {
            // Xử lý lỗi khác
            model.addAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "user/set-password"; // Tên của view kết quả (ví dụ: reset-password-result.html)
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        model.addAttribute("user", new Users());
        if (session == null || session.getAttribute("user") == null) {
            return "user/login";
        }
        Users loginInfo = (Users) session.getAttribute("user");
        Users user = new Users();
        if (loginInfo != null) {
            user.setId(loginInfo.getId());
            user.setName(loginInfo.getName());
            user.setEmail(loginInfo.getEmail());
            user.setPhone(loginInfo.getPhone());
            user.setAddress(loginInfo.getAddress());
            user.setAvatar(loginInfo.getAvatar());
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/editProfile")
    public String editUser(HttpSession session, Model model, @ModelAttribute("user") UserDTO1 user) throws IOException {
        // Lấy thông tin người dùng hiện tại từ backend
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        currentUser.setAddress(user.getAddress());
        currentUser.setStatus(Status.ACTIVE.getValue());
        currentUser.setUpdated(LocalDateTime.now());
        MultipartFile avatar = user.getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            String avatarFileName = avatar.getOriginalFilename();

            // Remove any invalid characters from the directory path
            String fileUploadDirectory = fileUpload.replace("\"", "");

            // Get just the file name without the directory path
            String safeFileName = Paths.get(avatarFileName).getFileName().toString();

            // Construct the full file path
            String fullPath = fileUploadDirectory + File.separator + safeFileName;

            // Xóa avatar cũ nếu có
            if (currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
                File oldFile = new File(fileUploadDirectory + File.separator + currentUser.getAvatar());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // Copy the file
            java.nio.file.Files.copy(avatar.getInputStream(), java.nio.file.Paths.get(fullPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Lưu lại tên file avatar mới
            currentUser.setAvatar(avatarFileName);
        }

        // Cập nhật người dùng
        rt.put(urlU + "/edit", currentUser, Users.class);

        // Cập nhật lại thông tin session
        session.setAttribute("user", currentUser);

        model.addAttribute("message", "Edit profile successfully.");
        return "user/profile"; // Chuyển hướng về trang danh sách người dùng sau khi cập nhật
    }

    @GetMapping("/security")
    public String security(HttpSession session, Model model) {
        model.addAttribute("user", new Users());
        if (session == null || session.getAttribute("user") == null) {
            return "user/login";
        }
        Users loginInfo = (Users) session.getAttribute("user");
        Users user = new Users();
        if (loginInfo != null) {

            user.setId(loginInfo.getId());
        }
        model.addAttribute("user", user);

        return "user/security";
    }

    @PostMapping("/userChangePassword")
    public String changePasswordUser(Model model, @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute("user") UserDTO1 user) throws IOException {
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        if (!currentUser.getPassword().equals(currentPassword)) {
            model.addAttribute("errorCurrent", "Current password is incorrect.");
            return "user/security"; // 
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "user/security"; // 
        }
        currentUser.setPassword(newPassword);
        rt.put(urlU + "/edit", currentUser);
        model.addAttribute("message", "Change password successfully.");
        return "user/security"; // 
    }

    @GetMapping("/order-history")
    public String orderHistory(HttpSession session, Model model) {
        if (session == null || session.getAttribute("user") == null) {
            return "user/login";
        }

        Users user = (Users) session.getAttribute("user");
        int userId = user.getId();
        model.addAttribute("userId", userId);

        // Lấy danh sách tất cả các đơn hàng
        ResponseEntity<List<Orders>> response = rt.exchange(
                urlO + "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Orders>>() {
        }
        );

        List<Orders> allOrders = response.getBody();

        // Lọc danh sách để chỉ lấy các đơn hàng có userId khớp với userId của người dùng đăng nhập
        List<Orders> userOrders = allOrders.stream()
                .filter(order -> order.getUserId() != null && order.getUserId().getId() == userId)
                .collect(Collectors.toList());
        model.addAttribute("list", userOrders);
        return "user/orderhistory";
    }
    
    

    @GetMapping("/order-details/{id}")
    public String orderDetails(Model model, @PathVariable("id") int id, HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return "user/login";
        }
        OrderDetails[] orderDetailsArray = rt.getForObject(urlOD + "/" + id, OrderDetails[].class);
        List<OrderDetails> orderDetailsList = Arrays.asList(orderDetailsArray);
        Orders orders = rt.getForObject(urlO + "/" + id, Orders.class);
        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetailsList);

        return "user/orderdetails";
    }
    
    

    @GetMapping("/error")
    public String showErrorPage() {
        return "user/error";
    }



    private Users checkIfEmailExists(String email) {
        try {
            ResponseEntity<Users> response = rt.getForEntity(urlU + "/" + email, Users.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e; // Ném ngoại lệ nếu gặp lỗi khác
        }
    }

}
