package com.bezkoder.springjwt.product.repository;

import com.bezkoder.springjwt.product.entity.CertificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationGroupRepository extends JpaRepository<CertificationGroup, Long> {
    List<CertificationGroup> findByUserId(Long userId);
    Optional<CertificationGroup> findByIdAndUserId(Long id, Long userId);
    boolean existsByUser_UsernameAndForceEmail(String username, String forceEmail);
}
