package com.bezkoder.springjwt.product.service;

import com.bezkoder.springjwt.auth.models.User;
import com.bezkoder.springjwt.auth.repository.UserRepository;
import com.bezkoder.springjwt.product.dto.CertificationGroupRequest;
import com.bezkoder.springjwt.product.dto.ProductUserRequest;
import com.bezkoder.springjwt.product.entity.CertificationGroup;
import com.bezkoder.springjwt.product.entity.ProductUser;
import com.bezkoder.springjwt.product.repository.CertificationGroupRepository;
import com.bezkoder.springjwt.product.repository.ProductUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificationGroupService {

    @Autowired
    private CertificationGroupRepository certificationGroupRepository;

    @Autowired
    private ProductUserRepository productUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final String VERIFY_URL_TEMPLATE = "http://univberry.site/verify/%s";

    @Transactional
    public CertificationGroup createCertificationGroup(String username, CertificationGroupRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (certificationGroupRepository.existsByUser_UsernameAndForceEmail(username, request.getForceEmail())) {
            throw new DataIntegrityViolationException("Force email already exists");
        }

        CertificationGroup group = new CertificationGroup();
        group.setForceEmail(request.getForceEmail());
        group.setUser(user);
        return certificationGroupRepository.save(group);
    }

    @Transactional
    public ProductUser createProductUser(String username, Long groupId, ProductUserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CertificationGroup group = certificationGroupRepository.findByIdAndUserId(groupId, user.getId())
                .orElseThrow(() -> new RuntimeException("Certification Group not found"));

        ProductUser productUser = new ProductUser();
        productUser.setEmail(request.getEmail().replaceAll("@.*", "")+group.getForceEmail());
        productUser.setVerified(false);
        productUser.setCertificationGroup(group);

        productUser = productUserRepository.save(productUser);

        // 이메일 전송
        sendVerificationEmail(productUser);

        return productUser;
    }

    private void sendVerificationEmail(ProductUser productUser) {
        try {
            String verifyUrl = String.format(VERIFY_URL_TEMPLATE, productUser.getId());
            String subject = "Verify your email";
            String text = String.format("Dear User,\n\nPlease verify your email by clicking the link below:\n\n%s\n\nBest Regards,\nTeam", verifyUrl);

            emailService.sendEmail(productUser.getEmail(), subject, text);
            System.out.println("Verification email sent to " + productUser.getEmail());
        } catch (Exception e) {
            System.out.println("Failed to send verification email to " + productUser.getEmail());
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List<ProductUser> getAllProductUsers(String username, Long groupId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CertificationGroup group = certificationGroupRepository.findByIdAndUserId(groupId, user.getId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return group.getProductUsers();
    }

    @Transactional(readOnly = true)
    public List<CertificationGroup> getCertificationGroupsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return certificationGroupRepository.findByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public List<ProductUser> getProductUsersByGroupId(Long groupId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CertificationGroup group = certificationGroupRepository.findByIdAndUserId(groupId, user.getId())
                .orElseThrow(() -> new RuntimeException("Certification Group not found"));

        return group.getProductUsers();
    }
}
