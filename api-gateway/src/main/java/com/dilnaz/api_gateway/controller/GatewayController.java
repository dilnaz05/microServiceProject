package com.dilnaz.api_gateway.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String BOOK_SERVICE = "http://localhost:8082";
    private final String FILE_SERVICE = "http://localhost:8081";

    // ---------------- Book Service passthrough ----------------

    @GetMapping("/books")
    public ResponseEntity<?> getBooks() {
        return restTemplate.getForEntity(BOOK_SERVICE + "/books", Object.class);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        return restTemplate.getForEntity(BOOK_SERVICE + "/books/" + id, Object.class);
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody Object book) {
        return restTemplate.postForEntity(BOOK_SERVICE + "/books", book, Object.class);
    }

    // ---------------- File Service passthrough ----------------

    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        // Файлды RestTemplate Multipart форматына дайындау
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = FILE_SERVICE + "/files/upload";
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @GetMapping("/files/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        String url = FILE_SERVICE + "/files/download/" + filename;
        return restTemplate.getForEntity(url, Object.class);
    }

    // ---------------- Health check ----------------

    @GetMapping("/health")
    public String health() {
        return "API Gateway OK";
    }
}
