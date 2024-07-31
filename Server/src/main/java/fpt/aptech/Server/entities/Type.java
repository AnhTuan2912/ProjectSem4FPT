/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Server.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Type")
@NamedQueries({
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t"),
    @NamedQuery(name = "Type.findById", query = "SELECT t FROM Type t WHERE t.id = :id"),
    @NamedQuery(name = "Type.findByName", query = "SELECT t FROM Type t WHERE t.name = :name")})
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @JoinTable(name = "Products_Type", joinColumns = {
        @JoinColumn(name = "type_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "product_id", referencedColumnName = "id")})
    @OneToMany
    private List<Products> productsList;

    public Type() {
    }

    public Type(Integer id) {
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

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

  


    
}
