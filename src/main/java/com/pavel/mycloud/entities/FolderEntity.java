package com.pavel.mycloud.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "folders")
public class FolderEntity extends CompositeFileEntity {
    @ManyToOne
    private FolderEntity parentFolder;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_folder_id")
    private Set<FileEntity> containedFiles;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_folder_id")
    private Set<FolderEntity> containedFolders;


    public FolderEntity() {
    }

    public FolderEntity(String name, String owner, LocalDateTime creationDate) {
        super(name, owner, creationDate);
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

    public FolderEntity getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderEntity parentFolder) {
        this.parentFolder = parentFolder;
    }
}
