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
import java.util.List;
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

    public String create(FolderDto folder) {
        FolderEntity folderEntity = folderEntityFactory.createFolderEntity(folder);

        FolderEntity savedFolder = folderRepository.save(folderEntity);
        return savedFolder.getUuid();
    }

    public Collection<BaseEntityDTO> findAllContentByParentFolder(String parenFolderId) {
        Collection<CompositeFileEntity> content = new ArrayList<>();

        content.addAll(folderRepository.findAllByParentFolderId(parenFolderId));
        content.addAll(fileRepository.findAllByParentFolderId(parenFolderId));

        return content.stream().map(e -> new BaseEntityDTO(e, parenFolderId)).collect(Collectors.toList());
    }

    public String createHomeFolderForUser(UserDTO userDTO) {
        FolderDto folderDto = new FolderDto()
                .setName(userDTO.getName())
                .setParentFolderId("10000000-0000-0000-0000-000000000001")
                .setOwner(userDTO.getName());

        return create(folderDto);
    }

    public Collection<BaseEntityDTO> listByUuid(String uuid) {
        Collection<CompositeFileEntity> content = new ArrayList<>();

        FolderEntity folderEntity = folderRepository.findByUuid(uuid);
        content.addAll(folderEntity.getContainedFolders());
        content.addAll(folderEntity.getContainedFiles());

        List<BaseEntityDTO> result = new ArrayList<>();
        if (!isMainRootDirectory(folderEntity)) {
            BaseEntityDTO parent = new BaseEntityDTO(folderEntity.getParentFolder(), uuid).setName("/..");
            result.add(parent);
        }
        result.addAll(content.stream().map(e -> new BaseEntityDTO(e, uuid)).collect(Collectors.toList()));

        return result;
    }

    public FolderEntity findByUuid(String uuid) {
        return folderRepository.findByUuid(uuid);
    }

    private boolean isMainRootDirectory(FolderEntity folderEntity) {
        return folderEntity.getParentFolder().getUuid().equals("10000000-0000-0000-0000-000000000001");
    }
}
