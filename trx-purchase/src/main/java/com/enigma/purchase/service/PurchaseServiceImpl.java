package com.enigma.purchase.service;

import com.enigma.purchase.constant.OtherServiceUrl;
import com.enigma.purchase.dto.Book;
import com.enigma.purchase.entity.Purchase;
import com.enigma.purchase.exception.DataNotFoundException;
import com.enigma.purchase.repository.PurchaseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Transactional
    public Purchase createTransaction(Purchase purchase) throws JsonProcessingException {
        Book book = getBookFromOtherService(purchase);
        if(book.getStock() == 0){
            throw new DataNotFoundException("Book not available");
        }

        if(book.getStock() < purchase.getQuantity()){
            throw new DataNotFoundException("Book stock quantity is insufficient for your order quantity");

        }
        purchase.setPriceSell(book.getPrice() * purchase.getQuantity());
        book.setStock(book.getStock() - purchase.getQuantity());
        updateBookStockFromOtherService(book);
        return purchaseRepository.save(purchase);
    }

    private Book getBookFromOtherService(Purchase purchase) throws JsonProcessingException{
        String url = OtherServiceUrl.BOOK_SERVICE +"/"+ purchase.getBookId();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String bookString = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        return new ObjectMapper().readValue(bookString, Book.class);
    }

    private void updateBookStockFromOtherService(Book book){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);

        restTemplate.exchange(OtherServiceUrl.BOOK_SERVICE, HttpMethod.PUT, entity, String.class).getBody();
    }
}
