package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.ShareDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.factories.FileEntityFactory;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileEntityFactory fileFactory;

    private final StorageService storageService;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, FileEntityFactory fileFactory, StorageService storageService,
                       UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.fileFactory = fileFactory;
        this.storageService = storageService;
        this.userRepository = userRepository;
    }

    public void upload(CreateFileDTO fileDTO) {
        FileEntity fileEntity = fileFactory.createFileEntity(fileDTO);

        fileRepository.save(fileEntity);
        storageService.uploadFile(fileDTO.setUuid(fileEntity.getUuid()));
    }

    public Collection<BaseEntityDTO> findAllFiles() {
        Collection<BaseEntityDTO> fileDTOS = new HashSet<>();

        for (FileEntity file : fileRepository.findAll()) {
            fileDTOS.add(new BaseEntityDTO(file));
        }
        return fileDTOS;
    }

    public Collection<BaseEntityDTO> findAllByParentFolder() {
        Collection<BaseEntityDTO> folderDTOS = new HashSet<>();

        for (FileEntity file : fileRepository.findAll()) {
            folderDTOS.add(new BaseEntityDTO(file));
        }
        return folderDTOS;
    }

    public FileEntity findByUuid(String uuid) {
        FileEntity file = fileRepository.findByUuid(uuid);
        String key = file.isShared() ? file.getShareSourceUuid() : uuid;
        byte[] content = storageService.downloadFile(key, file.getName());
        file.setContent(content);
        return file;
    }

    public String delete(String uuid) {
        fileRepository.deleteByUuid(uuid);

        return "File Removed";
    }

    public String share(ShareDTO dto) {
        Optional<UserEntity> shareUser = userRepository.findByName(dto.getShareWith());
        if (shareUser.isPresent()) {
            FileEntity fileEntity = fileFactory.createSharedFile(dto, shareUser.get());

            fileRepository.save(fileEntity);
            return "File " + dto.getUuid() + " shared with " + dto.getShareWith();
        }

        return "User " + dto.getShareWith() + "not found!";
    }
}
