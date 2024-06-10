package com.bezkoder.springjwt.product.repository;

import com.bezkoder.springjwt.product.entity.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, String> {
}
