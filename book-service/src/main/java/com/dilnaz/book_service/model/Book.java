package com.dilnaz.book_service.model;

import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String fileName;
    private LocalDateTime createdAt;

    public Book() {}

    public Book(Long id, String title, String author,
                String description, String fileName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
