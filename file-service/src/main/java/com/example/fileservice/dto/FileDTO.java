package com.example.fileservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private Long id;
    private String fileName;
    private String originalName;
    private long size;
    private LocalDateTime addedTime;
    private String mimeType;
}
