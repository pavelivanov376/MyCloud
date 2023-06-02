package com.pavel.mycloud.dtos;

public class FolderDto {
    private int id;
    private String name;
    private String parentFolderId;

    private String owner;

    public String getOwner() {
        return owner;
    }

    public FolderDto setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public FolderDto setName(String name) {
        this.name = name;
        return this;
    }


    public String getParentFolderId() {
        return parentFolderId;
    }

    public String getFullPathWithoutFolderName() {
        return parentFolderId.substring(0, parentFolderId.lastIndexOf('/'));
    }

    public String getParentFolderName() {
        return parentFolderId.substring(parentFolderId.lastIndexOf('/') + 1);
    }

    public FolderDto setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
        return this;
    }

    public int getId() {
        return id;
    }

    public FolderDto setId(int id) {
        this.id = id;
        return this;
    }
}
