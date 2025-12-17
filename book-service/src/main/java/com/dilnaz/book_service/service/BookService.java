package com.dilnaz.book_service.service;

import com.dilnaz.book_service.model.Book;
import com.dilnaz.book_service.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book update(Long id, Book book) {
        return repository.update(id, book);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
