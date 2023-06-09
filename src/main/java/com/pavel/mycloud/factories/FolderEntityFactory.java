package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FolderEntityFactory {
    private final FolderFinder folderFinder;
    private final FolderRepository folderRepository;


    public FolderEntityFactory(FolderFinder folderFinder,
                               FolderRepository folderRepository) {
        this.folderFinder = folderFinder;
        this.folderRepository = folderRepository;
    }

    public FolderEntity createFolderEntity(FolderDto folderDto) {
        FolderEntity folderEntity = new FolderEntity();

        folderEntity.setName("/" + folderDto.getName());
        folderEntity.setOwner(folderDto.getOwner());

        FolderEntity parentFolder = folderRepository.findByUuid(folderDto.getParentFolderId());
        folderEntity.setParentFolder(parentFolder);
        folderEntity.setUuid(UUID.randomUUID().toString());
        folderEntity.setCreationDate(LocalDateTime.now());

        return folderEntity;
    }
}
