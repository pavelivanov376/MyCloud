package com.pavel.mycloud.helpers;

import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.stereotype.Component;

@Component
public class FolderFinder {

    private final FolderRepository folderRepository;

    public FolderFinder(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public FolderEntity find(String path) {
        String folderName = path.substring(path.lastIndexOf('/') + 1);

        return folderRepository.findByName(folderName);
    }

    public FolderEntity findFolderRecursively(String path, FolderEntity parentFolder) {
        int slashIndex = path.indexOf('/');
        if (slashIndex < 0) {
            return folderRepository.findByName(path); // return parentFolder
        }

        String folderName = path.substring(0, slashIndex);

        FolderEntity childFolder = parentFolder.getContainedFolders().stream().filter(folder -> folderName.equals(folder.getName())).findFirst().get();

        return findFolderRecursively(path.substring(0, slashIndex), childFolder);
    }


}
