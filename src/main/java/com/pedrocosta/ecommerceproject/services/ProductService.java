package com.pedrocosta.ecommerceproject.services;

import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.repositories.BaseRepository;
import com.pedrocosta.ecommerceproject.repositories.ProductRepository;
import com.pedrocosta.ecommerceproject.utils.FindEnumValue;
import com.pedrocosta.ecommerceproject.utils.StringIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrocosta.ecommerceproject.entities.Product;

import java.util.List;

@Service
public class ProductService extends BaseService<Product> {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(BaseRepository<Product> repository) {
        super(repository);
    }

    @Override
    public Product save(Product entity) {
        if (entity == null) {
            return null;
        }
        entity.setCode(StringIdGenerator.generateProductCode(productRepository, entity.getType()));
        return super.save(entity);
    }

    public Product searchByBarcode(String barcode) {
        if (barcode.length() < 13 || barcode.length() > 13) {
            throw new Error("Wrong barcode length");
        }

        try {
            Long.parseLong(barcode);
            return productRepository.findByBarcode(barcode);
        } catch (NumberFormatException ex) {
            throw new Error("Wrong barcode format");
        }
    }

    public List<Product> searchByType(String type) {
        PRODUCT_TYPE enum_type = FindEnumValue.lookup(PRODUCT_TYPE.class, type.toUpperCase());
        return productRepository.searchByType(enum_type);
    }
}
