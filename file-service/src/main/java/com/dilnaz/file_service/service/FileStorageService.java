package com.dilnaz.file_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir);

        try {
            if (Files.notExists(this.uploadDir)) {
                Files.createDirectories(this.uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Cannot initialize upload directory: " + this.uploadDir, e
            );
        }
    }

    public String upload(MultipartFile file) {
        try {
            Path target = uploadDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public Resource download(String filename) throws MalformedURLException {
        Path filePath = uploadDir.resolve(filename).normalize();
        return new UrlResource(filePath.toUri());
    }
}

