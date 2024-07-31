/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "Orders")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByName", query = "SELECT o FROM Orders o WHERE o.name = :name"),
    @NamedQuery(name = "Orders.findByEmail", query = "SELECT o FROM Orders o WHERE o.email = :email"),
    @NamedQuery(name = "Orders.findByPhone", query = "SELECT o FROM Orders o WHERE o.phone = :phone"),
    @NamedQuery(name = "Orders.findByAddress", query = "SELECT o FROM Orders o WHERE o.address = :address"),
    @NamedQuery(name = "Orders.findByCountry", query = "SELECT o FROM Orders o WHERE o.country = :country"),
    @NamedQuery(name = "Orders.findByState", query = "SELECT o FROM Orders o WHERE o.state = :state"),
    @NamedQuery(name = "Orders.findByCity", query = "SELECT o FROM Orders o WHERE o.city = :city"),
    @NamedQuery(name = "Orders.findByPostcode", query = "SELECT o FROM Orders o WHERE o.postcode = :postcode"),
    @NamedQuery(name = "Orders.findByNote", query = "SELECT o FROM Orders o WHERE o.note = :note"),
    @NamedQuery(name = "Orders.findByPaymentMethod", query = "SELECT o FROM Orders o WHERE o.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByTotalQuantity", query = "SELECT o FROM Orders o WHERE o.totalQuantity = :totalQuantity"),
    @NamedQuery(name = "Orders.findByTotalPrice", query = "SELECT o FROM Orders o WHERE o.totalPrice = :totalPrice")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "note")
    private String note;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;
    @Column(name = "status")
    private Integer status;
    @Column(name = "total_quantity")
    private Integer totalQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_price")
    private double totalPrice;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;

    public Orders() {
    }

    public Orders(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    
    
    public Orders(Integer id, String name, String email, String phone, String address, String country, String state, String city, String postcode, String note, String paymentMethod, LocalDateTime orderDate, Integer status, Integer totalQuantity, double totalPrice, Users userId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postcode = postcode;
        this.note = note;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.status = status;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

   

 

 
    
}
