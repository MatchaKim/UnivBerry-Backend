package com.bezkoder.springjwt.product.repository;

import com.bezkoder.springjwt.auth.models.User;
import com.bezkoder.springjwt.auth.repository.UserRepository;
import com.bezkoder.springjwt.product.dto.CertificationGroupRequest;
import com.bezkoder.springjwt.product.dto.ProductUserRequest;
import com.bezkoder.springjwt.product.entity.CertificationGroup;
import com.bezkoder.springjwt.product.entity.ProductUser;
import com.bezkoder.springjwt.product.service.CertificationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CertificationGroupService certificationGroupService;

    @Autowired
    private ProductUserRepository productUserRepository;

    @Override
    public void run(String... args) throws Exception {
        // 예제 사용자 생성
        User user = new User("username", "user@example.com", "password");
        userRepository.save(user);

        // 예제 인증 그룹 생성
        CertificationGroupRequest groupRequest1 = new CertificationGroupRequest();
        groupRequest1.setForceEmail("example.com");
        CertificationGroup group1 = certificationGroupService.createCertificationGroup(user.getUsername(), groupRequest1);

        CertificationGroupRequest groupRequest2 = new CertificationGroupRequest();
        groupRequest2.setForceEmail("this.com");
        CertificationGroup group2 = certificationGroupService.createCertificationGroup(user.getUsername(), groupRequest2);

        // 예제 인증 그룹에 ProductUser 추가
        addProductUserToGroup(group1, "productuser1@example.com");
        addProductUserToGroup(group1, "productuser2@example.com");
        addProductUserToGroup(group2, "productuser3@example.com");
    }

    private void addProductUserToGroup(CertificationGroup group, String email) {
        ProductUserRequest productUserRequest = new ProductUserRequest();
        productUserRequest.setEmail(email);
        ProductUser productUser = new ProductUser();
        productUser.setEmail(productUserRequest.getEmail());
        productUser.setVerified(false);
        productUser.setCertificationGroup(group);
        productUserRepository.save(productUser);
    }
}
