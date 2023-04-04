package com.pavel.mycloud.dtos;

import com.pavel.mycloud.entities.FileEntity;

import java.time.LocalDateTime;

public class BaseEntityDTO {

    private String name;
    private String parentFolder;
    private String owner;
    private String type;
    private LocalDateTime creationDate;

    public BaseEntityDTO(FileEntity file) {
        this.name = file.getName();
        this.parentFolder = file.getParentFolder() != null ? file.getParentFolder().getName() : null;
        this.owner = file.getOwner();
        this.type = file.getType();
        this.creationDate = file.getCreationDate();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
