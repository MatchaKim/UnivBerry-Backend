package com.bezkoder.springjwt.product.entity;

import com.bezkoder.springjwt.auth.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "certification_groups")
public class CertificationGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String forceEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "certificationGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductUser> productUsers;

    public CertificationGroup() {
    }

    public CertificationGroup(String forceEmail, User user) {
        this.forceEmail = forceEmail;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForceEmail() {
        return forceEmail;
    }

    public void setForceEmail(String forceEmail) {
        this.forceEmail = forceEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProductUser> getProductUsers() {
        return productUsers;
    }

    public void setProductUsers(List<ProductUser> productUsers) {
        this.productUsers = productUsers;
    }
}
