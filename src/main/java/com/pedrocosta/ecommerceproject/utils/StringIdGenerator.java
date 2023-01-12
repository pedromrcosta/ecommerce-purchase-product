package com.pedrocosta.ecommerceproject.utils;

import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.repositories.ProductRepository;

public class StringIdGenerator {

    public static String generateProductCode(ProductRepository productRepository, PRODUCT_TYPE type) {

        Integer nextCodeNumber = productRepository.countByType(type) + 1;
        Integer zerosToPad = 6 - nextCodeNumber.toString().length();
        String typeAsString = type.toString();

        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(typeAsString.charAt(0));
        codeBuilder.append(typeAsString.charAt(typeAsString.length() - 1));

        for (int i = 0; i < zerosToPad; i++) {
            codeBuilder.append("0");
        }

        codeBuilder.append(nextCodeNumber);
        return codeBuilder.toString();
    }

}
