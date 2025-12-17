package com.dilnaz.book_service.repository;

import com.dilnaz.book_service.model.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final Map<Long, Book> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // CREATE
    public Book save(Book book) {
        Long id = idGenerator.getAndIncrement();
        book.setId(id);
        book.setCreatedAt(java.time.LocalDateTime.now());
        storage.put(id, book);
        return book;
    }

    // READ ALL
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    // READ BY ID
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    // UPDATE
    public Book update(Long id, Book book) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException("Book not found");
        }
        book.setId(id);
        book.setCreatedAt(storage.get(id).getCreatedAt());
        storage.put(id, book);
        return book;
    }

    // DELETE
    public void delete(Long id) {
        storage.remove(id);
    }
}
