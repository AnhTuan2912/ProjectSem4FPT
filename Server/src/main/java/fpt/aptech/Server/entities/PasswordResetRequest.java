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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "password_reset_request")
@NamedQueries({
    @NamedQuery(name = "PasswordResetRequest.findAll", query = "SELECT p FROM PasswordResetRequest p"),
    @NamedQuery(name = "PasswordResetRequest.findById", query = "SELECT p FROM PasswordResetRequest p WHERE p.id = :id"),
    @NamedQuery(name = "PasswordResetRequest.findByEmail", query = "SELECT p FROM PasswordResetRequest p WHERE p.email = :email"),
    @NamedQuery(name = "PasswordResetRequest.findByToken", query = "SELECT p FROM PasswordResetRequest p WHERE p.token = :token")})
public class PasswordResetRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @Lob
    @Column(name = "request_time")
    private LocalDateTime requestTime;

    public PasswordResetRequest() {
    }

    public PasswordResetRequest(Integer id) {
        this.id = id;
    }

    public PasswordResetRequest( String email, String token, LocalDateTime requestTime) {

        this.email = email;
        this.token = token;
        this.requestTime = requestTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    

}
