package com.example.fileservice.controller;


import com.example.fileservice.dto.FileDTO;
import com.example.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String upload(@RequestPart("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }



    @GetMapping("/download/{hashName}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String hashName) {
        FileDTO fileDTO = fileService.getFileByName(hashName);
        if (fileDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ByteArrayResource resource = fileService.download(hashName);
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String originalName = fileDTO.getOriginalName();
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalName.substring(dotIndex);
        }

        String downloadName = hashName + extension;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
                .contentLength(fileDTO.getSize())
                .contentType(MediaType.parseMediaType(fileDTO.getMimeType()))
                .body(resource);
    }


    @GetMapping
    public List<FileDTO> getFiles(){
        return fileService.getFiles();
    }

    @GetMapping("/{id}")
    public FileDTO getFile(@PathVariable Long id){
        return fileService.getFile(id);
    }

    @GetMapping("file-name/{fileName}")
    public FileDTO getFileByName(@PathVariable String fileName){
        return fileService.getFileByName(fileName);
    }



}
