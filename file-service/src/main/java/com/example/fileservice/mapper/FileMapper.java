package com.example.fileservice.mapper;


import com.example.fileservice.dto.FileDTO;
import com.example.fileservice.model.File;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDTO toDto(File file);
    File toEntity(FileDTO dto);
    List<FileDTO> toDtoList(List<File> list);

}
