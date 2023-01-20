package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.CreateFolderDto;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.services.FolderService;
import org.springframework.stereotype.Component;

@Component
public class FolderEntityFactory {
    private final FolderFinder folderFinder;


    public FolderEntityFactory(FolderFinder folderFinder) {
        this.folderFinder = folderFinder;
    }

    public FolderEntity createFolderEntity(CreateFolderDto folderDto) {
        FolderEntity folderEntity = new FolderEntity();

        folderEntity.setName(folderDto.getFullPath());
        folderEntity.setOwner("Pavel");

        FolderEntity parentFolder = folderFinder.find(folderDto.getFullPath());
        folderEntity.setParentFolder(parentFolder);

        return folderEntity;
    }
}
