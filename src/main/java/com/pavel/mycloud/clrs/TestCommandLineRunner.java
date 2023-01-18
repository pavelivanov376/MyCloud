package com.pavel.mycloud.clrs;

import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import com.pavel.mycloud.repositories.FileRepository;
import com.pavel.mycloud.repositories.FolderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@Component
public class TestCommandLineRunner implements CommandLineRunner {
    private FileRepository fileRepository;
    private FolderRepository folderRepository;


    public TestCommandLineRunner(FileRepository fileRepository, FolderRepository folderRepository) {
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Simulate a folder with contents
//
//        FileEntity file = new FileEntity();
//        file.setName("file1");
//        file.setPath("path1");
//        file.setOwner("pavel");
//
//        FolderEntity rootFolder = new FolderEntity();
//        rootFolder.setName("rootFolder");
//        rootFolder.setPath("path2");
//        rootFolder.setOwner("pavel");
//
//        FolderEntity innerFolder = new FolderEntity();
//        innerFolder.setName("innerFolder");
//        innerFolder.setPath("path3");
//        innerFolder.setOwner("pavel");
//
//        HashSet<FileEntity> fileset = new HashSet<>();
//        fileset.add(file);
//
//        HashSet<FolderEntity> folderset = new HashSet<>();
//        folderset.add(innerFolder);
//
//        rootFolder.setContainedFiles(fileset);
//        rootFolder.setContainedFolders(folderset);
//
//        fileRepository.save(file);
//        folderRepository.save(innerFolder);
//        folderRepository.save(rootFolder);

        //TODO Persist them and see what's inside the db and probably comment out @JoiningOn()
    }
}
