package com.pedrocosta.ecommerceproject.repositories;

import com.pedrocosta.ecommerceproject.entities.Purchase;
import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends BaseRepository<Purchase>{

    List<Purchase> findAllByPurchaseDateBetween(LocalDate begin, LocalDate end);

    /* * * * * * * * * * * * * * * * * * * * * * * * * *
    * * * * * * * * * * PLANE SQL * * * * * * * * * * *
    SELECT DISTINCT p.*
    FROM PURCHASES p
    JOIN PURCHASE_PRODUCT pp ON p.id = pp.purchase_id
    JOIN PRODUCTS pr ON pp.product_id = pr.id
    WHERE pr.TYPE = 0;
    * * * * * * * * * * * * * * * * * * * * * * * * * */
    List<Purchase> findByProductType(PRODUCT_TYPE type);
}
