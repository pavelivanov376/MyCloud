package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.entities.CompositeFileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.factories.FolderEntityFactory;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderFinder folderFinder;
    private final FolderEntityFactory folderEntityFactory;
    private final FileRepository fileRepository;

    public FolderService(FolderRepository folderRepository, FolderFinder folderFinder, FolderEntityFactory folderEntityFactory,
                         FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.folderFinder = folderFinder;
        this.folderEntityFactory = folderEntityFactory;
        this.fileRepository = fileRepository;
    }

    public FolderEntity findByPath(String path) {
        return folderFinder.find(path);
    }

    public void create(FolderDto folder) {
        FolderEntity folderEntity = folderEntityFactory.createFolderEntity(folder);
        folderRepository.save(folderEntity);
    }

    public Collection<BaseEntityDTO> findAllContentByParentFolder(String parenFolderId) {        //TODO Return everyting that has this folder as a parent folder
        Collection<CompositeFileEntity> content = new ArrayList<>();

        content.addAll(folderRepository.findAllByParentFolderId(parenFolderId));
        content.addAll(fileRepository.findAllByParentFolderId(parenFolderId));

        return content.stream().map(e -> new BaseEntityDTO(e, parenFolderId)).collect(Collectors.toList());
    }
}
