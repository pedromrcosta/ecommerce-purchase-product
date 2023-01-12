package com.pedrocosta.ecommerceproject.controllers;

import com.pedrocosta.ecommerceproject.entities.Purchase;
import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.services.BaseService;
import com.pedrocosta.ecommerceproject.services.PurchaseService;
import com.pedrocosta.ecommerceproject.utils.FindEnumValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("purchase-products")
public class PurchaseProductController extends BaseController<Purchase> {

    @Autowired
    private PurchaseService purchaseService;

    public PurchaseProductController(BaseService<Purchase> service) {
        super(service);
    }

    @GetMapping(params = {"type"})
    public List<Purchase> getPurchasesByProductType(@RequestParam String type) {
        PRODUCT_TYPE enum_type = FindEnumValue.lookup(PRODUCT_TYPE.class, type.toUpperCase());
        return purchaseService.searchPurchaseByProductType(enum_type);
    }
}
