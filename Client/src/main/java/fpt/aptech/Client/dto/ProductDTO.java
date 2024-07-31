/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.dto;

import fpt.aptech.Client.entities.Categories;
import fpt.aptech.Client.entities.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public class ProductDTO {
    private Integer id;

    private String name;

    private String code;

    private String gfm;

    private String lt;

    private String gs;

    private String sft;

    private String lw;

    private String cd;

    private String fl;

    private MultipartFile image;

    private MultipartFile extraImage1;

    private MultipartFile extraImage2;

    private MultipartFile extraImage3;


    private double price;

    private Integer quantity;

    private Integer discount;


    private LocalDateTime created;


    private LocalDateTime updated;

    private Integer status;


    private Categories categoryId;


    private Type typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGfm() {
        return gfm;
    }

    public void setGfm(String gfm) {
        this.gfm = gfm;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public String getSft() {
        return sft;
    }

    public void setSft(String sft) {
        this.sft = sft;
    }

    public String getLw() {
        return lw;
    }

    public void setLw(String lw) {
        this.lw = lw;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getExtraImage1() {
        return extraImage1;
    }

    public void setExtraImage1(MultipartFile extraImage1) {
        this.extraImage1 = extraImage1;
    }

    public MultipartFile getExtraImage2() {
        return extraImage2;
    }

    public void setExtraImage2(MultipartFile extraImage2) {
        this.extraImage2 = extraImage2;
    }

    public MultipartFile getExtraImage3() {
        return extraImage3;
    }

    public void setExtraImage3(MultipartFile extraImage3) {
        this.extraImage3 = extraImage3;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    public Type getTypeId() {
        return typeId;
    }

    public void setTypeId(Type typeId) {
        this.typeId = typeId;
    }

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, String code, String gfm, String lt, String gs, String sft, String lw, String cd, String fl, MultipartFile image, MultipartFile extraImage1, MultipartFile extraImage2, MultipartFile extraImage3, double price, Integer quantity, Integer discount, LocalDateTime created, LocalDateTime updated, Integer status, Categories categoryId, Type typeId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.gfm = gfm;
        this.lt = lt;
        this.gs = gs;
        this.sft = sft;
        this.lw = lw;
        this.cd = cd;
        this.fl = fl;
        this.image = image;
        this.extraImage1 = extraImage1;
        this.extraImage2 = extraImage2;
        this.extraImage3 = extraImage3;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.categoryId = categoryId;
        this.typeId = typeId;
    }
    
    
    
    
}
