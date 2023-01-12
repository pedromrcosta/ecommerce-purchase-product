package com.pedrocosta.ecommerceproject.services;

import com.pedrocosta.ecommerceproject.entities.Product;
import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSave(){
        Product newProduct = new Product("Test Product", PRODUCT_TYPE.GAME,123.123);
        Product repositoryProduct = new Product("Test Product", PRODUCT_TYPE.GAME,123.123);

        when(productRepository.save(any())).thenReturn(repositoryProduct);
        Product productTest = productService.save(newProduct);

        assertEquals(productTest, repositoryProduct);
    }

    @Test
    void shouldSaveAll(){
        List<Product> saveList = new ArrayList<>();
        saveList.add(new Product("Test Product", PRODUCT_TYPE.GAME,123.123));
        saveList.add(new Product("Test Product", PRODUCT_TYPE.GAME,123.123));

        when(productRepository.saveAll(any())).thenReturn(saveList);
        List<Product> productTest = productService.saveAll(saveList);

        assertEquals(productTest, saveList);
    }

    @Test
    void shouldNotSave(){
        Product newProduct = null;

        Product productTest = productService.save(newProduct);

        assertEquals(productTest, null);
    }

    @Test
    void shouldGetAll() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(
            new Product("Test Product 1", 111.00),
            new Product("Test Product 2", 222.00)
        ));

        List<Product> result = productService.getAll();

        assertEquals("Test Product 1", result.get(0).getName());
        assertEquals(222.00, result.get(1).getPrice());
    }

    @Test
    void shouldGet() {
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product("Test Product 1", 111.00)));

        Product  result = productService.get(1);

        assertEquals("Test Product 1", result.getName());
    }

    @Test
    void shouldNotGet() {
        Product result = productService.get(1);

        assertEquals(null, result);
    }
}
