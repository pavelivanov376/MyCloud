package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.ShareFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.services.FolderService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FileEntityFactory {
    private final FolderService folderService;
    private final FileRepository fileRepository;

    public FileEntityFactory(FolderService folderService,
                             FileRepository fileRepository) {
        this.folderService = folderService;
        this.fileRepository = fileRepository;
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
        fileEntity.setOwner(fileDTO.getOwner());
        fileEntity.setType(extractFileType(filename));
        fileEntity.setCreationDate(LocalDateTime.now());
        fileEntity.setUuid(UUID.randomUUID().toString());

        FolderEntity parentFolder = folderService.findByUuid(fileDTO.getParentFolderId());
        fileEntity.setParentFolder(parentFolder);

        return fileEntity;
    }

    private String extractFileType(String filename) {
        int indexOfDot = filename.indexOf('.');
        return filename.substring(indexOfDot + 1);
    }

    public FileEntity createSharedFile(ShareFileDTO dto, UserEntity shareUser) {
        String shareFolderUuid = shareUser.getShareFolderUuid();
        FolderEntity parentFolder = folderService.findByUuid(shareFolderUuid);

        FileEntity file = fileRepository.findByUuid(dto.getUuid());
        file.setParentFolder(parentFolder);
        file.setOwner(dto.getShareWith());
        file.setShareSourceUuid(file.isShared() ? file.getShareSourceUuid() : file.getUuid());
        file.setUuid(UUID.randomUUID().toString());
        file.setShared(true);
        file.setId(null);

        return file;
    }
}
