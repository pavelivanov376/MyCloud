package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.CreateFolderDto;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.factories.FolderEntityFactory;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderFinder folderFinder;
    private final FolderEntityFactory folderEntityFactory;

    public FolderService(FolderRepository folderRepository, FolderFinder folderFinder, FolderEntityFactory folderEntityFactory) {
        this.folderRepository = folderRepository;
        this.folderFinder = folderFinder;
        this.folderEntityFactory = folderEntityFactory;
    }

    public FolderEntity findByPath(String path) {

        return folderFinder.find(path);
    }

    public void create(CreateFolderDto folder) {
        FolderEntity folderEntity = folderEntityFactory.createFolderEntity(folder);
        folderRepository.save(folderEntity);
    }
}
