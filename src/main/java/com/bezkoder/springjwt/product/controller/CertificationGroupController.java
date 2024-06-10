package com.bezkoder.springjwt.product.controller;

import com.bezkoder.springjwt.product.dto.CertificationGroupRequest;
import com.bezkoder.springjwt.product.dto.ProductUserRequest;
import com.bezkoder.springjwt.product.entity.CertificationGroup;
import com.bezkoder.springjwt.product.entity.ProductUser;
import com.bezkoder.springjwt.product.service.CertificationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certification-group")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CertificationGroupController {

    @Autowired
    private CertificationGroupService certificationGroupService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createCertificationGroup(@RequestBody CertificationGroupRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            CertificationGroup group = certificationGroupService.createCertificationGroup(username, request);
            return ResponseEntity.ok(group);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Force email already exists");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{groupId}/product-user")
    public ResponseEntity<?> createProductUser(@PathVariable Long groupId, @RequestBody ProductUserRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            ProductUser user = certificationGroupService.createProductUser(username, groupId, request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{groupId}/product-users")
    public ResponseEntity<?> getAllProductUsers(@PathVariable Long groupId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<ProductUser> users = certificationGroupService.getAllProductUsers(username, groupId);
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-groups")
    public ResponseEntity<?> getCertificationGroupsByUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<CertificationGroup> groups = certificationGroupService.getCertificationGroupsByUsername(username);
            return ResponseEntity.ok(groups);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
