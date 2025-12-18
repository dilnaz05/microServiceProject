package com.dilnaz.api_gateway.controller;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


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

    /*
    // ---------------- FILE SERVICE ----------------
    // ЭТИ МЕТОДЫ ЗАКОММЕНТИРОВАНЫ, ТАК КАК ОБРАБОТКА ФАЙЛОВ
    // ТЕПЕРЬ ВЫПОЛНЯЕТСЯ НАПРЯМУЮ ЧЕРЕЗ КОНФИГУРАЦИЮ ШЛЮЗА В application.yml
    // ЭТО БОЛЕЕ НАДЕЖНЫЙ СПОСОБ ПРОКСИРОВАНИЯ ФАЙЛОВ.

    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadFile(@RequestPart("file") FilePart filePart) {

        System.out.println("Filename: " + filePart.filename());
        System.out.println("Content-Type: " + filePart.headers().getContentType());

        return webClient.post()
                .uri(FILE_SERVICE + "/file/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", filePart))
                .retrieve()
                .bodyToMono(String.class);
    }



    @GetMapping("/file/download/{filename}")
    public Mono<ResponseEntity<byte[]>> downloadFile(@PathVariable String filename) {

        return webClient.get()
                .uri(FILE_SERVICE + "/file/download/" + filename)
                .retrieve()
                .toEntity(byte[].class);
    }
    */

    // ---------------- HEALTH ----------------

    @GetMapping("/health")
    public String health() {
        return "API Gateway OK";
    }
}
