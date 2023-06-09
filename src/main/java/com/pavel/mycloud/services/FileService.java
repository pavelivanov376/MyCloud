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
        fileDTO.setUuid(fileEntity.getUuid());

        fileRepository.save(fileEntity);
        storageService.uploadFile(fileDTO);
    }

    public Collection<BaseEntityDTO> findAllFiles() {
        Collection<BaseEntityDTO> fileDTOS = new HashSet<>();

        for (FileEntity file : fileRepository.findAll()) {
            fileDTOS.add(new BaseEntityDTO(file));
        }
        return fileDTOS;
    }
    public Collection<BaseEntityDTO> findAllByParentFolder() {
        Collection<BaseEntityDTO> folderDTOS = new HashSet<>();

        for (FileEntity file : fileRepository.findAll()) {
            folderDTOS.add(new BaseEntityDTO(file));
        }
        return folderDTOS;
    }

    public FileEntity findByUuid(String uuid) {
        FileEntity file = fileRepository.findByUuid(uuid);
        byte[] content = storageService.downloadFile(uuid, file.getName());
        file.setContent(content);
        return file;
    }
}
