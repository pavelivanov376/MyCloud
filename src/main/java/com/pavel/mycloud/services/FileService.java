package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(CreateFileDTO fileDTO);
    void upload(MultipartFile file);

FileEntity download(Long id);
}
