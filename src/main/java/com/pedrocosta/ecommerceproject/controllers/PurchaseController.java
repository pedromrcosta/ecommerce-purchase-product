package com.pedrocosta.ecommerceproject.controllers;

import com.pedrocosta.ecommerceproject.entities.Purchase;
import com.pedrocosta.ecommerceproject.enums.MESSAGES;
import com.pedrocosta.ecommerceproject.services.BaseService;
import com.pedrocosta.ecommerceproject.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("purchase")
public class PurchaseController extends BaseController<Purchase> {

    @Autowired
    private PurchaseService purchaseService;

    public PurchaseController(BaseService<Purchase> service) {
        super(service);
    }

    @GetMapping(params = {"beginDate", "endDate"})
    public ResponseEntity<List<Purchase>> findAllByPurchaseDateBetween(@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate beginDate, @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate) {
        List<Purchase> purchasesByDate = purchaseService.findAllByPurchaseDateBetween(beginDate, endDate);

        if (purchasesByDate.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(purchasesByDate, HttpStatus.FOUND);
    }

    @GetMapping("/{id}/delivery-status")
    public ResponseEntity<String> getDeliveryStatus(@PathVariable int id) {
        String status = purchaseService.getDeliveryStatus(id);

        if (status.isEmpty()) {
            return new ResponseEntity<>(MESSAGES.PURCHASE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(status, HttpStatus.FOUND);
    }
}
