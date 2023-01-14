package com.pavel.mycloud.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "folders")
public class FolderEntity extends BaseFileEntity {
    @OneToMany
    @JoinColumn(name = "folders_id")
    private Set<FileEntity> containedFiles;
    @OneToMany
    @JoinColumn(name = "folders_id")
    private Set<FolderEntity> containedFolders;


    public FolderEntity() {
    }

    public FolderEntity(String name, String path, String owner, LocalDateTime creationDate) {
        super(name, path, owner, creationDate);
    }

    public Set<FileEntity> getContainedFiles() {
        return containedFiles;
    }

    public void setContainedFiles(Set<FileEntity> containedFiles) {
        this.containedFiles = containedFiles;
    }

    public Set<FolderEntity> getContainedFolders() {
        return containedFolders;
    }

    public void setContainedFolders(Set<FolderEntity> containedFolders) {
        this.containedFolders = containedFolders;
    }
}
