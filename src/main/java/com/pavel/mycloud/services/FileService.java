package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.factories.FileEntityFactory;
import com.pavel.mycloud.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileEntityFactory fileFactory;

    private final StorageService storageService;

    public FileService(FileRepository fileRepository, FileEntityFactory fileFactory, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.fileFactory = fileFactory;
        this.storageService = storageService;
    }

    public void upload(CreateFileDTO fileDTO) {
        FileEntity fileEntity = fileFactory.createFileEntity(fileDTO);

        fileRepository.save(fileEntity);
        storageService.uploadFile(fileDTO.getContent());
    }

    public FileEntity download(String name) {
        FileEntity file = fileRepository.findByName(name);
        byte[] content = storageService.downloadFile(name);
        file.setContent(content);
        return file;
    }

    public Collection<BaseEntityDTO> findAllFiles() {
        Collection<BaseEntityDTO> downloadFileDTOS = new HashSet<>();

        for (FileEntity file : fileRepository.findAll()) {
            downloadFileDTOS.add(new BaseEntityDTO(file));
        }
        return downloadFileDTOS;
    }
}
