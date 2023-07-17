package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.ShareDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FileEntityFactory {
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;

    public FileEntityFactory(FileRepository fileRepository,
                             FolderRepository folderRepository) {
        this.fileRepository = fileRepository;
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
        fileEntity.setOwner(fileDTO.getOwner());
        fileEntity.setType(extractFileType(filename));
        fileEntity.setCreationDate(LocalDateTime.now());
        fileEntity.setUuid(UUID.randomUUID().toString());

        FolderEntity parentFolder = folderRepository.findByUuid(fileDTO.getParentFolderId());
        fileEntity.setParentFolder(parentFolder);

        return fileEntity;
    }

    private String extractFileType(String filename) {
        int indexOfDot = filename.indexOf('.');
        return filename.substring(indexOfDot + 1);
    }

    public FileEntity createSharedFile(ShareDTO dto, UserEntity shareUser) {
        String shareFolderUuid = shareUser.getShareFolderUuid();
        FolderEntity parentFolder = folderRepository.findByUuid(shareFolderUuid);

        FileEntity file = fileRepository.findByUuid(dto.getUuid());
        file.setUuid("shared_from_" + file.getOwner() + "_" + UUID.randomUUID().toString());
        file.setOwner(dto.getShareWith());
        file.setParentFolder(parentFolder);
        file.setShareSourceUuid(file.isShared() ? file.getShareSourceUuid() : file.getUuid());
        file.setShared(true);
        file.setId(null);

        return file;
    }
}
