package com.pavel.mycloud.clrs;

import com.pavel.mycloud.entities.BaseFileEntity;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;
import org.springframework.boot.CommandLineRunner;

import java.util.HashSet;

public class TestCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //TODO: Simulate a folder with contents

        FileEntity file = new FileEntity();
        FolderEntity rootFolder = new FolderEntity();
        FolderEntity innerFolder = new FolderEntity();

        HashSet<FileEntity> fileset = new HashSet<>();
        fileset.add(file);

        HashSet<FolderEntity> folderset = new HashSet<>();
        folderset.add(innerFolder);

        rootFolder.setContainedFiles(fileset);
        rootFolder.setContainedFolders(folderset);

        //TODO Persist them and see what's inside the db and probably comment out @JoiningOn()
    }
}
