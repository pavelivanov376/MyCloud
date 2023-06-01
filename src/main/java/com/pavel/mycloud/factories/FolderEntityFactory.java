package com.pavel.mycloud.factories;

import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.helpers.FolderFinder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FolderEntityFactory {
    private final FolderFinder folderFinder;


    public FolderEntityFactory(FolderFinder folderFinder) {
        this.folderFinder = folderFinder;
    }

    public FolderEntity createFolderEntity(FolderDto folderDto) {
        FolderEntity folderEntity = new FolderEntity();

        folderEntity.setName("/" + folderDto.getName());
        folderEntity.setOwner(folderDto.getOwner());//TODO Put the actual name of the owner

        FolderEntity parentFolder = folderFinder.find(folderDto.getFullPath());
        folderEntity.setParentFolder(parentFolder);
        folderEntity.setUuid(UUID.randomUUID().toString());

        return folderEntity;
    }
}
