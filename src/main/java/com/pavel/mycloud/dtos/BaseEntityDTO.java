package com.pavel.mycloud.dtos;

import com.pavel.mycloud.entities.CompositeFileEntity;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.entities.FolderEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntityDTO {

    private String name;
    private String parentFolder;
    private String owner;
    private String type;
    private LocalDateTime creationDate;

    private String uuid;

    public BaseEntityDTO(FileEntity file) {
        this.name = file.getName();
        this.parentFolder = file.getParentFolder() != null ? file.getParentFolder().getName() : null;
        this.owner = file.getOwner();
        this.type = file.getType();
        this.creationDate = file.getCreationDate();
        this.uuid = file.getUuid();
    }

    public BaseEntityDTO(CompositeFileEntity file, String parentFolder) {
        this.name = file.getName();
        this.parentFolder = parentFolder;
        this.owner = file.getOwner();
        this.creationDate = file.getCreationDate();
        this.type = file instanceof FolderEntity ? "directory" : "file";
        this.uuid = file.getUuid();
    }

    public String getUuid() {
        return uuid;
    }

    public BaseEntityDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public BaseEntityDTO setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseEntityDTO setName(String name) {
        this.name = name;
        return this;
    }


    public String getParentFolder() {
        return parentFolder;
    }

    public BaseEntityDTO setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public BaseEntityDTO setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getType() {
        return type;
    }

    public BaseEntityDTO setType(String type) {
        this.type = type;
        return this;
    }

}
