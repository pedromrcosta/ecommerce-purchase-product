package com.pedrocosta.ecommerceproject.controllers;

import com.pedrocosta.ecommerceproject.services.BaseService;
import com.pedrocosta.ecommerceproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pedrocosta.ecommerceproject.entities.Product;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController<Product> {

    @Autowired
    private ProductService productService;

    public ProductController(BaseService<Product> service) {
        super(service);
    }

    @GetMapping(params = {"barcode"})
    public ResponseEntity<Product> searchByBarcode(@RequestParam String barcode) {
        Product productByBarcode = productService.searchByBarcode(barcode);

        if (productByBarcode == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productByBarcode, HttpStatus.FOUND);
    }

    @GetMapping(params = {"type"})
    public ResponseEntity<List<Product>> searchByType(@RequestParam String type) {
        List<Product> productsByType = productService.searchByType(type);

        if (productsByType.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productsByType, HttpStatus.FOUND);
    }
}
