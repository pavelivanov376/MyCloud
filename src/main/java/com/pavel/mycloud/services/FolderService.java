package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.dtos.ShareDTO;
import com.pavel.mycloud.dtos.UserDTO;
import com.pavel.mycloud.entities.CompositeFileEntity;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.factories.FolderEntityFactory;
import com.pavel.mycloud.helpers.FolderFinder;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.FolderRepository;
import com.pavel.mycloud.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderFinder folderFinder;
    private final FolderEntityFactory folderFactory;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FolderService(FolderRepository folderRepository, FolderFinder folderFinder, FolderEntityFactory folderEntityFactory,
                         FileRepository fileRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.folderFinder = folderFinder;
        this.folderFactory = folderEntityFactory;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public FolderEntity findByPath(String path) {
        return folderFinder.find(path);
    }

    public String create(FolderDto folder) {
        FolderEntity folderEntity = folderFactory.createFolderEntity(folder);

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
    public String createSharedFolderForUser(UserDTO userDTO, String homeFolderId) {
        FolderDto folderDto = new FolderDto()
                .setName("shared")
                .setParentFolderId(homeFolderId)
                .setOwner(userDTO.getName());

        return create(folderDto);
    }

    public Collection<BaseEntityDTO> listByUuid(String uuid) {
        Collection<CompositeFileEntity> content = new LinkedHashSet<>();

        FolderEntity folderEntity = folderRepository.findByUuid(uuid);
        content.addAll(folderEntity.getContainedFolders());
        content.addAll(folderEntity.getContainedFiles());

        List<BaseEntityDTO> result = new ArrayList<>();
        if (!isMainRootDirectory(folderEntity)) {
            BaseEntityDTO parent = new BaseEntityDTO(folderEntity.getParentFolder(), uuid).setName("/..");
            result.add(parent);
        }
        result.addAll(content.stream().map(e -> new BaseEntityDTO(e, uuid)).collect(Collectors.toList()));
        result.sort(Comparator.comparing(BaseEntityDTO::getName));
        return result;
    }

    public FolderEntity findByUuid(String uuid) {
        return folderRepository.findByUuid(uuid);
    }

    private boolean isMainRootDirectory(FolderEntity folderEntity) {
        return folderEntity.getParentFolder().getUuid().equals("10000000-0000-0000-0000-000000000001");
    }

    public String delete(String uuid) {
        folderRepository.deleteByUuid(uuid);
        return "Folder deleted";
    }

    public String share(ShareDTO dto) {
        Optional<UserEntity> shareUser = userRepository.findByName(dto.getShareWith());
        if (shareUser.isPresent()) {
            FolderEntity folderEntity = folderFactory.createSharedFolder(dto, shareUser.get());

            folderRepository.save(folderEntity);
            return "Folder " + dto.getUuid() + " shared with " + dto.getShareWith();
        }

        return "User " + dto.getShareWith() + "not found!";

    }
}
