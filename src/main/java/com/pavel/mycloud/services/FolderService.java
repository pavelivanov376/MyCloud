package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.dtos.UserDTO;
import com.pavel.mycloud.entities.CompositeFileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.factories.FolderEntityFactory;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
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

    public Long create(FolderDto folder) {
        FolderEntity folderEntity = folderEntityFactory.createFolderEntity(folder);
        FolderEntity savedFolder = folderRepository.save(folderEntity);
        return savedFolder.getId();
    }

    public Collection<BaseEntityDTO> findAllContentByParentFolder(String parenFolderId) {        //TODO Return everyting that has this folder as a parent folder
        Collection<CompositeFileEntity> content = new ArrayList<>();

        content.addAll(folderRepository.findAllByParentFolderId(parenFolderId));
        content.addAll(fileRepository.findAllByParentFolderId(parenFolderId));

        return content.stream().map(e -> new BaseEntityDTO(e, parenFolderId)).collect(Collectors.toList());
    }

    public Long createHomeFolderForUser(UserDTO userDTO) {
        FolderDto folderDto = new FolderDto()
        .setName(userDTO.getName())
        .setFullPath("/root")
        .setOwner(userDTO.getName());

        Long id = create(folderDto);
        //  String stringId = id.toString();//TODO Refactor when start using UUID

        return id;
    }

    public Collection<BaseEntityDTO> listById(Long folderId) {
        Collection<CompositeFileEntity> content = new ArrayList<>();

        FolderEntity folderEntity = folderRepository.findById(folderId).get();
        content.addAll(folderEntity.getContainedFolders());
        content.addAll(folderEntity.getContainedFiles());

        return content.stream().map(e -> new BaseEntityDTO(e, folderId.toString())).collect(Collectors.toList());
    }
}
