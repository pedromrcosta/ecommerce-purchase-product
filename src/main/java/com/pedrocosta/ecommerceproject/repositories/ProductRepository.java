package com.pedrocosta.ecommerceproject.repositories;

import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product>{

    @Query(nativeQuery = true, value = "select * from products p where p.barcode = :barcode")
    Product findByBarcodeQuery(String barcode);

    Product findByBarcode(String barcode);

    @Query(nativeQuery = true, value = "select count(*) from products where type = :type")
    Integer countByTypeQuery(PRODUCT_TYPE type);
    Integer countByType(PRODUCT_TYPE type);

    List<Product> searchByType(PRODUCT_TYPE type);
}
