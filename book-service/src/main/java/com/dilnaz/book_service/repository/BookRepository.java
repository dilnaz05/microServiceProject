package com.dilnaz.book_service.repository;

import com.dilnaz.book_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}

