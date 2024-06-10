package com.bezkoder.springjwt.product.controller;


import com.bezkoder.springjwt.product.dto.ProductUserResponse;
import com.bezkoder.springjwt.product.entity.ProductUser;
import com.bezkoder.springjwt.product.repository.ProductUserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/product-user")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductUserController {

    private final ProductUserRepository productUserRepository;


    @GetMapping("/verify/product/{productUserId}")
    public ProductUserResponse verify(@PathVariable String productUserId) {

        Optional<ProductUser> productUser = productUserRepository.findById(productUserId);

        if (productUser.isPresent()) {
            ProductUser productUserData = productUser.get();
            productUserData.setVerified(true);

            productUserRepository.save(productUserData);

            return new ProductUserResponse(productUserData.getEmail(), true, true);
        } else {
            return new ProductUserResponse(null, false, false);
        }
    }


    @GetMapping("/check/product/{productUserId}")
    public ProductUserResponse checkVerified(@PathVariable String productUserId) {
        Optional<ProductUser> productUser = productUserRepository.findById(productUserId);

        if (productUser.isPresent()) {
            ProductUser user = productUser.get();
            return new ProductUserResponse(user.getEmail(), user.isVerified(),true);
        } else {
            return new ProductUserResponse("", false,false);
        }
    }

}
