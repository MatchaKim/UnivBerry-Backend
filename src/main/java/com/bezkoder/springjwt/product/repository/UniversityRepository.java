package com.bezkoder.springjwt.product.repository;

import com.bezkoder.springjwt.product.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByNameContainingIgnoreCase(String name);
}
