/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Products")
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findById", query = "SELECT p FROM Products p WHERE p.id = :id"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByCode", query = "SELECT p FROM Products p WHERE p.code = :code"),
    @NamedQuery(name = "Products.findByGfm", query = "SELECT p FROM Products p WHERE p.gfm = :gfm"),
    @NamedQuery(name = "Products.findByLt", query = "SELECT p FROM Products p WHERE p.lt = :lt"),
    @NamedQuery(name = "Products.findByGs", query = "SELECT p FROM Products p WHERE p.gs = :gs"),
    @NamedQuery(name = "Products.findBySft", query = "SELECT p FROM Products p WHERE p.sft = :sft"),
    @NamedQuery(name = "Products.findByLw", query = "SELECT p FROM Products p WHERE p.lw = :lw"),
    @NamedQuery(name = "Products.findByCd", query = "SELECT p FROM Products p WHERE p.cd = :cd"),
    @NamedQuery(name = "Products.findByFl", query = "SELECT p FROM Products p WHERE p.fl = :fl"),
    @NamedQuery(name = "Products.findByImage", query = "SELECT p FROM Products p WHERE p.image = :image"),
    @NamedQuery(name = "Products.findByExtraImage1", query = "SELECT p FROM Products p WHERE p.extraImage1 = :extraImage1"),
    @NamedQuery(name = "Products.findByExtraImage2", query = "SELECT p FROM Products p WHERE p.extraImage2 = :extraImage2"),
    @NamedQuery(name = "Products.findByExtraImage3", query = "SELECT p FROM Products p WHERE p.extraImage3 = :extraImage3"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByQuantity", query = "SELECT p FROM Products p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "Products.findByDiscount", query = "SELECT p FROM Products p WHERE p.discount = :discount"),
    @NamedQuery(name = "Products.findByCreated", query = "SELECT p FROM Products p WHERE p.created = :created"),
    @NamedQuery(name = "Products.findByUpdated", query = "SELECT p FROM Products p WHERE p.updated = :updated"),
    @NamedQuery(name = "Products.findByStatus", query = "SELECT p FROM Products p WHERE p.status = :status")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "gfm")
    private String gfm;
    @Column(name = "lt")
    private String lt;
    @Column(name = "gs")
    private String gs;
    @Column(name = "sft")
    private String sft;
    @Column(name = "lw")
    private String lw;
    @Column(name = "cd")
    private String cd;
    @Column(name = "fl")
    private String fl;
    @Column(name = "image")
    private String image;
    @Column(name = "extra_image1")
    private String extraImage1;
    @Column(name = "extra_image2")
    private String extraImage2;
    @Column(name = "extra_image3")
    private String extraImage3;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "discount")
    private Integer discount;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;
    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Categories categoryId;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne
    private Type typeId;

    public Products() {
    }

    public Products(Integer id) {
        this.id = id;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExtraImage1() {
        return extraImage1;
    }

    public void setExtraImage1(String extraImage1) {
        this.extraImage1 = extraImage1;
    }

    public String getExtraImage2() {
        return extraImage2;
    }

    public void setExtraImage2(String extraImage2) {
        this.extraImage2 = extraImage2;
    }

    public String getExtraImage3() {
        return extraImage3;
    }

    public void setExtraImage3(String extraImage3) {
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

    public Products(Integer id, String name, String code, String gfm, String lt, String gs, String sft, String lw, String cd, String fl, String image, String extraImage1, String extraImage2, String extraImage3, double price, Integer quantity, Integer discount, LocalDateTime created, LocalDateTime updated, Integer status, Categories categoryId, Type typeId) {
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
