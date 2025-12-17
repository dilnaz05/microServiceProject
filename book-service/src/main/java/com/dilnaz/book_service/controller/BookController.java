package com.dilnaz.book_service.controller;

import com.dilnaz.book_service.model.Book;
import com.dilnaz.book_service.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.create(book);
    }

    // READ ALL
    @GetMapping
    public List<Book> getAll() {
        return service.getAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return service.update(id, book);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Book deleted successfully";
    }

    // HEALTH CHECK
    @GetMapping("/health")
    public String health() {
        return "Book Service OK";
    }
}
