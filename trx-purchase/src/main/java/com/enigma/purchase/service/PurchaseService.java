package com.enigma.purchase.service;

import com.enigma.purchase.entity.Purchase;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PurchaseService {
    Purchase createTransaction(Purchase purchase) throws JsonProcessingException;
}
