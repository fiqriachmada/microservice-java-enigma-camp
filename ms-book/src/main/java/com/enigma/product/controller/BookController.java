package com.enigma.product.controller;

import com.enigma.product.entity.Book;
import com.enigma.product.service.BookService;
import com.enigma.product.utils.PageResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookByid(@PathVariable String id){
        return bookService.getBookById(id);
    }


    @PutMapping
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    @DeleteMapping
    public void  deleteBook(@RequestParam String id){
        bookService.deleteBook(id);
    }

    @GetMapping
    public PageResponseWrapper<Book> getBookPerPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "3") Integer size,
                                                    @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> bookPage = bookService.getBookPerPage(pageable);
        return new PageResponseWrapper<>(bookPage);
    }

}
