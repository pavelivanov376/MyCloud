package com.pavel.mycloud.dtos;

public class FolderDto {
    private int id;
    private String name;
    private String fullPath;

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


    public String getFullPath() {
        return fullPath;
    }

    public String getFullPathWithoutFolderName() {
        return fullPath.substring(0, fullPath.lastIndexOf('/'));
    }

    public String getParentFolderName() {
        return fullPath.substring(fullPath.lastIndexOf('/') + 1);
    }

    public FolderDto setFullPath(String fullPath) {
        this.fullPath = fullPath;
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
