/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.Client.controller.admin;

import fpt.aptech.Client.dto.ProductDTO;
import fpt.aptech.Client.entities.Categories;
import fpt.aptech.Client.entities.Orders;
import fpt.aptech.Client.entities.Products;
import fpt.aptech.Client.entities.Type;
import fpt.aptech.Client.enums.Status;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Controller
@ControllerAdvice

@RequestMapping("/admin")
public class PostPutDelete {

    String urlU = "http://localhost:9999/api/users";
    String urlC = "http://localhost:9999/api/categories";
    String urlT = "http://localhost:9999/api/type";
    String urlP = "http://localhost:9999/api/products";
    String urlO = "http://localhost:9999/api/orders";
    RestTemplate rt = new RestTemplate();
    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping("/categoriesC")
    public String pageCateC(Model model) {
        model.addAttribute("cate", new Categories());
        return "admin/categories/categoriesC";
    }

    @RequestMapping(value = "/categoriesC", method = RequestMethod.POST)
    public String createCate(@Valid @ModelAttribute("cate") Categories cate,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cate", cate);
            return "admin/categories/categoriesC";
        }

        // Kiểm tra xem danh mục đã tồn tại hay chưa
        Categories cateExits = checkIfCategoryExists(cate.getName());
        if (cateExits != null) {
            result.rejectValue("name", null, "CateName is Exits");
        }
        if (result.hasErrors()) {
            model.addAttribute("cate", cate);
            return "admin/categories/categoriesC";
        }

        // Tạo danh mục mới
        rt.postForEntity(urlC + "/create", cate, Categories.class).getBody();

        return "redirect:/admin/categories";
    }

    @GetMapping("/categoriesU/{id}")
    public String editC(Model model, @PathVariable int id) {
        Categories cate = rt.getForObject(urlC + "/id/" + id, Categories.class);
        model.addAttribute("cate", cate);
        return "admin/categories/categoriesU";

    }

    @PostMapping("/categoriesU")
    public String editC(Model model, @Valid @ModelAttribute("cate") Categories cate, BindingResult result) {

        Categories cateExits = checkIfCategoryExists(cate.getName());
        if (cateExits != null) {
            result.rejectValue("name", null, "CateName is Exits");
        }
        if (result.hasErrors()) {
            model.addAttribute("cate", cate);
            return "admin/categories/categoriesU";
        }
        rt.put(urlC + "/edit", cate, Categories.class);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categoriesD/{id}")
    public String deleteC(Model model, @PathVariable int id) {
        rt.delete(urlC + "/" + id, Categories.class);
        return "redirect:/admin/categories";

    }

    @GetMapping("/typeC")
    public String pageTypeC(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type/typeC";
    }

    @RequestMapping(value = "/typeC", method = RequestMethod.POST)
    public String createType(@Valid @ModelAttribute("type") Type type,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("type", type);
            return "admin/type/typeC";
        }

        // Kiểm tra xem danh mục đã tồn tại hay chưa
        Type typeExits = checkIfTypeExists(type.getName());
        if (typeExits != null) {
            result.rejectValue("name", null, "Type is Exits");
        }
        if (result.hasErrors()) {
            model.addAttribute("type", type);
            return "admin/type/typeC";
        }

        // Tạo danh mục mới
        rt.postForEntity(urlT + "/create", type, Type.class).getBody();

        return "redirect:/admin/types";
    }

    @GetMapping("/typeU/{id}")
    public String editT(Model model, @PathVariable int id) {
        Type type = rt.getForObject(urlT + "/id/" + id, Type.class);
        model.addAttribute("type", type);
        return "admin/type/typeU";

    }

    @PostMapping("/typeU")
    public String editT(Model model, @Valid @ModelAttribute("type") Type type, BindingResult result) {

        Type typeExits = checkIfTypeExists(type.getName());
        if (typeExits != null) {
            result.rejectValue("name", null, "TypeName is Exits");
        }
        if (result.hasErrors()) {
            model.addAttribute("type", type);
            return "admin/type/typeU";
        }
        rt.put(urlT + "/edit", type, Type.class);
        return "redirect:/admin/types";
    }

    @GetMapping("/typeD/{id}")
    public String deleteT(Model model, @PathVariable int id) {
        rt.delete(urlT + "/" + id, Type.class);
        return "redirect:/admin/types";

    }

    @GetMapping("/productC")
    public String pageProductC(Model model) {
        List<Type> tList = rt.getForObject(urlT + "/", List.class);
        List<Categories> cList = rt.getForObject(urlC + "/", List.class);
        model.addAttribute("type", tList);
        model.addAttribute("cate", cList);
        model.addAttribute("pro", new Products());
        return "admin/product/productC";
    }

    @PostMapping("/productC")
    public String pageProductC(Model model, ProductDTO product) throws IOException {
        MultipartFile[] photos = {product.getImage(), product.getExtraImage1(), product.getExtraImage2(), product.getExtraImage3()}; // thêm các ảnh mới
        String[] fileNames = new String[photos.length];

        for (int i = 0; i < photos.length; i++) {
            MultipartFile multipartFile = photos[i];
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();

                // Remove any invalid characters from the directory path
                String fileUploadDirectory = fileUpload.replace("\"", "");

                // Get just the file name without the directory path
                String safeFileName = Paths.get(fileName).getFileName().toString();

                // Construct the full file path
                String fullPath = fileUploadDirectory + File.separator + safeFileName;

                // Copy the file
                java.nio.file.Files.copy(multipartFile.getInputStream(), java.nio.file.Paths.get(fullPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                fileNames[i] = fileName;
            } else {
                fileNames[i] = null; // hoặc gán giá trị mặc định nếu cần
            }
        }
        Products p = new Products(product.getId(), product.getName(), product.getCode(), product.getGfm(), product.getLt(), product.getGs(), product.getSft(), product.getLw(), product.getCd(), product.getFl(), fileNames[0], fileNames[1], fileNames[2], fileNames[3], product.getPrice(), product.getQuantity(), product.getDiscount(), LocalDateTime.now(), LocalDateTime.now(), Status.DEACTIVE.getValue(), product.getCategoryId(), product.getTypeId());
        // Save the product here using your preferred method
        ResponseEntity<Products> response = rt.postForEntity(urlP + "/create", p, Products.class);
        model.addAttribute("pro", response.getBody());
        return "redirect:/admin/products";
    }

    @GetMapping("/productU/{id}")
    public String editP(Model model, @PathVariable int id) {
        Products product = rt.getForObject(urlP + "/" + id, Products.class);
        List<Type> tList = rt.getForObject(urlT + "/", List.class);
        List<Categories> cList = rt.getForObject(urlC + "/", List.class);
        model.addAttribute("type", tList);
        model.addAttribute("cate", cList);
        model.addAttribute("pro", product);

        return "admin/product/productU";

    }

    @PostMapping("/edit")
    public String editP(Model model, ProductDTO product) throws IOException {
        Products currentProduct = rt.getForObject(urlP + "/" + product.getId(), Products.class);

        MultipartFile[] photos = {product.getImage(), product.getExtraImage1(), product.getExtraImage2(), product.getExtraImage3()};
        String[] currentImages = {
            currentProduct.getImage(),
            currentProduct.getExtraImage1(),
            currentProduct.getExtraImage2(),
            currentProduct.getExtraImage3()
        };
        String[] fileNames = new String[photos.length];

        for (int i = 0; i < photos.length; i++) {
            MultipartFile multipartFile = photos[i];
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();

                // Remove any invalid characters from the directory path
                String fileUploadDirectory = fileUpload.replace("\"", "");

                // Get just the file name without the directory path
                String safeFileName = Paths.get(fileName).getFileName().toString();

                // Construct the full file path
                String fullPath = fileUploadDirectory + File.separator + safeFileName;

                // Xóa ảnh cũ nếu có
                if (currentImages[i] != null && !currentImages[i].isEmpty()) {
                    File oldFile = new File(fileUploadDirectory + File.separator + currentImages[i]);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // Copy the file
                java.nio.file.Files.copy(multipartFile.getInputStream(), java.nio.file.Paths.get(fullPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                fileNames[i] = fileName;
            } else {
                fileNames[i] = currentImages[i]; // Giữ nguyên ảnh cũ nếu không có ảnh mới
            }
        }

        // Tạo đối tượng Products mới với thông tin cập nhật
        Products updatedProduct = new Products(product.getId(), product.getName(), product.getCode(), product.getGfm(), product.getLt(), product.getGs(), product.getSft(), product.getLw(), product.getCd(), product.getFl(), fileNames[0], fileNames[1], fileNames[2], fileNames[3], product.getPrice(), product.getQuantity(), product.getDiscount(), currentProduct.getCreated(), LocalDateTime.now(), currentProduct.getStatus(), product.getCategoryId(), product.getTypeId());

        // Cập nhật sản phẩm
        rt.put(urlP + "/edit", updatedProduct, Products.class);

        return "redirect:/admin/products";
    }

    @GetMapping("/productD/{id}")
    public String deleteP(Model model, @PathVariable int id) {
        rt.delete(urlP + "/" + id, Products.class);
        return "redirect:/admin/products";

    }

    @GetMapping("/staffD/{id}")
    public String deleteStaff(Model model, @PathVariable int id) {
        rt.delete(urlU + "/" + id, Products.class);
        return "redirect:/admin/staff";

    }
    
    @GetMapping("/customerD/{id}")
    public String deleteCustomer(Model model, @PathVariable int id) {
        rt.delete(urlU + "/" + id, Products.class);
        return "redirect:/admin/customer";

    }
    
    @GetMapping("/ordersD/{id}")
    public String deleteOrders(Model model, @PathVariable int id) {
        rt.delete(urlO + "/" + id, Orders.class);
        return "redirect:/admin/orders";

    }
    

    @PostMapping("/toggleStatus/{id}")
    public ResponseEntity<Void> toggleStatus(@PathVariable int id, @RequestBody Map<String, Integer> requestBody) {
        // Lấy sản phẩm hiện tại từ cơ sở dữ liệu
        Products product = rt.getForObject(urlP + "/" + id, Products.class);

        // Thay đổi trạng thái
        int status = requestBody.get("status");
        product.setStatus(status);

        // Cập nhật sản phẩm
        rt.put(urlP + "/edit", product, Products.class);

        return ResponseEntity.ok().build();
    }

    private Categories checkIfCategoryExists(String name) {
        try {
            ResponseEntity<Categories> response = rt.getForEntity(urlC + "/" + name, Categories.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e; // Ném ngoại lệ nếu gặp lỗi khác
        }
    }

    private Type checkIfTypeExists(String name) {
        try {
            ResponseEntity<Type> response = rt.getForEntity(urlT + "/" + name, Type.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e; // Ném ngoại lệ nếu gặp lỗi khác
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Categories.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Categories category = new Categories();
                category.setId(Integer.parseInt(text)); // Giả sử bạn sử dụng ID của category để chuyển đổi
                setValue(category);
            }
        });

        binder.registerCustomEditor(Type.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Type type = new Type();
                type.setId(Integer.parseInt(text)); // Giả sử bạn sử dụng ID của type để chuyển đổi
                setValue(type);
            }
        });
    }
}
