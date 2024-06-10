package com.bezkoder.springjwt.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUserResponse {

    private String email;

    private boolean verified;

    private boolean isEmailExisting;

}
