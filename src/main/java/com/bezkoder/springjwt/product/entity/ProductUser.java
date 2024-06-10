package com.bezkoder.springjwt.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "product_user")
public class ProductUser {
    @Id
    private String id;

    private String email;

    private boolean verified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_group_id")
    @JsonBackReference
    private CertificationGroup certificationGroup;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public ProductUser() {
    }

    public ProductUser(String id, String email, boolean verified, CertificationGroup certificationGroup) {
        this.id = id;
        this.email = email;
        this.verified = verified;
        this.certificationGroup = certificationGroup;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public CertificationGroup getCertificationGroup() {
        return certificationGroup;
    }

    public void setCertificationGroup(CertificationGroup certificationGroup) {
        this.certificationGroup = certificationGroup;
    }
}
