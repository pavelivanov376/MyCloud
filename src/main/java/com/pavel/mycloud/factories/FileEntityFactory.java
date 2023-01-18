package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class FileEntityFactory {
    private final FolderRepository folderRepository;

    public FileEntityFactory(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public FileEntity createFileEntity(CreateFileDTO fileDTO) {
        MultipartFile content = fileDTO.getContent();
        byte[] contentBytes;

        try {
            contentBytes = content.getBytes();
        } catch (IOException e) {
            System.out.println("=====> UNABLE TO BUILD FILE ENTITY <=====");
            throw new RuntimeException(e);
        }
        String filename = content.getOriginalFilename();
        FileEntity fileEntity = new FileEntity();

        fileEntity.setName(filename);
        fileEntity.setContent(contentBytes);
        fileEntity.setPath(fileDTO.getFullPath());
        fileEntity.setOwner("Pavel");
        fileEntity.setType(extractFileType(filename));
        fileEntity.setCreationDate(LocalDateTime.now());

        FolderEntity parentFolder = folderRepository.findByPath(fileDTO.getFullPath());
        fileEntity.setParentFolder(parentFolder);

        return fileEntity;
    }

    private String extractFileType(String filename) {
        int indexOfDot = filename.indexOf('.');
        return filename.substring(indexOfDot);
    }
}
