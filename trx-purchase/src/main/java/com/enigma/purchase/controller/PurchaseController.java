package com.enigma.purchase.controller;

import com.enigma.purchase.entity.Purchase;
import com.enigma.purchase.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/transaction")
    public Purchase createPurchase(@RequestBody Purchase purchase) throws JsonProcessingException {
        return purchaseService.createTransaction(purchase);
    }
}
