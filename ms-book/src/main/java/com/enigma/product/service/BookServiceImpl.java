package com.enigma.product.service;

import com.enigma.product.constant.ResponseMessage;
import com.enigma.product.entity.Book;
import com.enigma.product.exception.DataNotFoundException;
import com.enigma.product.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(String id) {
        verify(id);
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        verify(book.getId());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(String id) {
        verify(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getBookPerPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    private void verify(String id){
        if(!bookRepository.findById(id).isPresent()){
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "book", id);
            throw new DataNotFoundException(message);
        }
    }


}
