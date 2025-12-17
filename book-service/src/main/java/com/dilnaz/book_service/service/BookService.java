package com.dilnaz.book_service.service;

import com.dilnaz.book_service.model.Book;
import com.dilnaz.book_service.repository.BookRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public BookService(BookRepository bookRepository, RedisTemplate<String, Object> redisTemplate) {
        this.bookRepository = bookRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Book> getAllBooks() {
        String key = "books:all";
        List<Book> books = (List<Book>) redisTemplate.opsForValue().get(key);
        if (books == null) {
            books = bookRepository.findAll();
            redisTemplate.opsForValue().set(key, books);
        }
        return books;
    }

    public Book createBook(Book book) {
        Book saved = bookRepository.save(book);
        redisTemplate.delete("books:all");
        return saved;
    }
}
