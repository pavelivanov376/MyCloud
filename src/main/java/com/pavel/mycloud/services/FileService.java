package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void upload(CreateFileDTO fileDTO) {
        FileEntity fileEntity = null; //TODO get from factory

        fileRepository.save(fileEntity);
        //TODO return succeeded to upload response
    }

    public FileEntity download(Long id) {
        return fileRepository.findById(id).get();
    }
}
