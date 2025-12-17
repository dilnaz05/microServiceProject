package com.dilnaz.api_gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final WebClient webClient;

    @Value("${services.book}")
    private String BOOK_SERVICE;

    @Value("${services.file}")
    private String FILE_SERVICE;

    public GatewayController(WebClient webClient) {
        this.webClient = webClient;
    }

    // ---------------- BOOK SERVICE ----------------

    @GetMapping("/books")
    public Mono<Object> getBooks() {
        return webClient.get()
                .uri(BOOK_SERVICE + "/books")
                .retrieve()
                .bodyToMono(Object.class);
    }

    @PostMapping("/books")
    public Mono<Object> createBook(@RequestBody Object book) {
        return webClient.post()
                .uri(BOOK_SERVICE + "/books")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Object.class);
    }

    @GetMapping("/books/{id}")
    public Mono<Object> getBook(@PathVariable Long id) {
        return webClient.get()
                .uri(BOOK_SERVICE + "/books/" + id)
                .retrieve()
                .bodyToMono(Object.class);
    }

    // ---------------- FILE SERVICE ----------------

    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Object> uploadFile(@RequestPart("file") Mono<FilePart> file) {

        return file.flatMap(f ->
                webClient.post()
                        .uri(FILE_SERVICE + "/files/upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(BodyInserters.fromMultipartData("file", f))
                        .retrieve()
                        .bodyToMono(Object.class)
        );
    }

    @GetMapping("/files/download/{filename}")
    public Mono<ResponseEntity<byte[]>> downloadFile(@PathVariable String filename) {

        return webClient.get()
                .uri(FILE_SERVICE + "/files/download/" + filename)
                .retrieve()
                .toEntity(byte[].class);
    }

    // ---------------- HEALTH ----------------

    @GetMapping("/health")
    public String health() {
        return "API Gateway OK";
    }
}
