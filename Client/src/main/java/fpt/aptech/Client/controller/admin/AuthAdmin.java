/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.admin;

import fpt.aptech.Client.dto.UserDTO1;
import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.entities.Roles;
import fpt.aptech.Client.entities.Users;
import fpt.aptech.Client.enums.Role;
import fpt.aptech.Client.enums.Status;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/admin")
public class AuthAdmin {

    String urlU = "http://localhost:9999/api/users";
    String urlR = "http://localhost:9999/api/roles";
    RestTemplate rt = new RestTemplate();
    @Value("${upload.path}")
    private String fileUpload;

    @RequestMapping("/login")
    public String showLogin(Model model) {

        return "admin/login";
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
                if (user.getRoleId().getId() == Role.CUSTOMER.getValue()) {
                    session.invalidate();
                    return "admin/login";
                }
                if (user.getStatus() == Status.DEACTIVE.getValue()) {
                    session.invalidate();
                    model.addAttribute("error", "Your account has been blocked!");
                    return "admin/login";
                }
                return "redirect:/admin/dashboard";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "admin/login";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @PostMapping("/toggleStatusU/{id}")
    public ResponseEntity<Void> toggleStatus(@PathVariable int id, @RequestBody Map<String, Integer> requestBody) {
        // Lấy sản phẩm hiện tại từ cơ sở dữ liệu
        Users users = rt.getForObject(urlU + "/id/" + id, Users.class);

        // Thay đổi trạng thái
        int status = requestBody.get("status");
        users.setStatus(status);

        // Cập nhật sản phẩm
        rt.put(urlU + "/edit", users, Users.class);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/staffC")
    public String createUser(Model model) {
        List<Roles> rList = rt.getForObject(urlR + "/", List.class);
        model.addAttribute("role", rList);
        model.addAttribute("user", new Users());
        return "admin/staff/staffC";
    }

    @RequestMapping(value = "/staffC", method = RequestMethod.POST)
    public String createUsers(Model model, @ModelAttribute("user") UserDTO1 user, BindingResult result) throws IOException {
        List<Roles> rList = rt.getForObject(urlR + "/", List.class);
        model.addAttribute("role", rList);
        Users existingUser = checkIfEmailExists(user.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null, "Email already exists!");
            return "admin/staff/staffC"; // Return to an error page or input form page
        }

        MultipartFile multipartFile = user.getAvatar();
        // Check if the multipart file is empty
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();

            // Remove any invalid characters from the directory path
            String fileUploadDirectory = fileUpload.replace("\"", "");

            // Ensure the directory exists
            File uploadDir = new File(fileUploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();  // Create the directory if it doesn't exist
            }

            // Get just the file name without the directory path
            String safeFileName = Paths.get(fileName).getFileName().toString();

            // Construct the full file path
            String fullPath = fileUploadDirectory + File.separator + safeFileName;

            // Copy the file
            try {
                Files.copy(multipartFile.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // Handle file upload error
                e.printStackTrace();
                // Optionally, add an error message to the model
                model.addAttribute("errorMessage", "File upload failed: " + e.getMessage());
                return "admin/error"; // Return to an error page
            }

            // Reload the list of products and categories
            Users u = new Users(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getPhone(), user.getAddress(), fileName, LocalDateTime.now(), LocalDateTime.now(), Status.DEACTIVE.getValue(), user.getRoleId());
            ResponseEntity<Users> response = rt.postForEntity(urlU + "/create", u, Users.class);
            model.addAttribute("user", response.getBody());
            return "admin/staff";
        } else {
            // Create and save the user without the file name
            Users u = new Users(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getPhone(), user.getAddress(), null, LocalDateTime.now(), LocalDateTime.now(), Status.DEACTIVE.getValue(), user.getRoleId());
            ResponseEntity<Users> response = rt.postForEntity(urlU + "/create", u, Users.class);
            model.addAttribute("user", response.getBody());
        }
        return "redirect:/admin/staff";
    }

    @GetMapping("/staffU/{id}")
    public String editUser(Model model, @PathVariable int id) {
        Users user = rt.getForObject(urlU + "/id/" + id, Users.class);
        model.addAttribute("user", user);

        return "admin/staff/staffU";

    }

    @PostMapping("/staffU")
    public String editStaff(Model model, UserDTO1 user) throws IOException {
        // Lấy thông tin người dùng hiện tại từ backend
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        currentUser.setAddress(user.getAddress());
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

        // Tạo đối tượng Users mới với thông tin cập nhật
        // Cập nhật người dùng
        rt.put(urlU + "/edit", currentUser, Users.class);

        return "redirect:/admin/staff"; // Chuyển hướng về trang danh sách người dùng sau khi cập nhật
    }

    @GetMapping("/customerU/{id}")
    public String editCustomer(Model model, @PathVariable int id) {
        Users user = rt.getForObject(urlU + "/id/" + id, Users.class);
        model.addAttribute("user", user);

        return "admin/customer/customerU";

    }

    @PostMapping("/customerU")
    public String editCustomer(Model model, UserDTO1 user) throws IOException {
        // Lấy thông tin người dùng hiện tại từ backend
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        currentUser.setAddress(user.getAddress());
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

        // Tạo đối tượng Users mới với thông tin cập nhật
        // Cập nhật người dùng
        rt.put(urlU + "/edit", currentUser, Users.class);

        return "redirect:/admin/customer"; // Chuyển hướng về trang danh sách người dùng sau khi cập nhật
    }

    @PostMapping("/staffChangePassword")
    public String changePasswordStaff(Model model, @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute("user") UserDTO1 user) throws IOException {
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        if (!currentUser.getPassword().equals(currentPassword)) {
            model.addAttribute("errorCurrent", "Current password is incorrect.");
            return "admin/staff/staffU"; // 
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "admin/staff/staffU";
        }
        currentUser.setPassword(newPassword);
        rt.put(urlU + "/edit", currentUser);
        return "redirect:/admin/staff";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        model.addAttribute("user", new Users());
        if (session == null || session.getAttribute("user") == null) {
            return "admin/login";
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
        return "admin/profile";
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
        return "admin/profile"; // Chuyển hướng về trang danh sách người dùng sau khi cập nhật
    }

    @GetMapping("/security")
    public String security(HttpSession session, Model model) {
        model.addAttribute("user", new Users());
        if (session == null || session.getAttribute("user") == null) {
            return "admin/login";
        }
        Users loginInfo = (Users) session.getAttribute("user");
        Users user = new Users();
        if (loginInfo != null) {

            user.setId(loginInfo.getId());
        }
        model.addAttribute("user", user);

        return "admin/security";
    }

    @PostMapping("/userChangePassword")
    public String changePasswordUser(Model model, @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute("user") UserDTO1 user) throws IOException {
        Users currentUser = rt.getForObject(urlU + "/id/" + user.getId(), Users.class);
        if (!currentUser.getPassword().equals(currentPassword)) {
            model.addAttribute("errorCurrent", "Current password is incorrect.");
            return "admin/security"; // 
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "admin/security"; // 
        }
        currentUser.setPassword(newPassword);
        rt.put(urlU + "/edit", currentUser);
        model.addAttribute("message", "Change password successfully.");
        return "admin/security"; // 
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

    @InitBinder
    public void initBinder(WebDataBinder binder
    ) {
        binder.registerCustomEditor(Roles.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Roles roles = new Roles();
                roles.setId(Integer.parseInt(text)); // Giả sử bạn sử dụng ID của category để chuyển đổi
                setValue(roles);
            }
        });

    }
}
