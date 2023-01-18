package com.pavel.mycloud.services;

import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public FolderEntity findByPath(String path) {
        return folderRepository.findByPath(path);
    }
}
