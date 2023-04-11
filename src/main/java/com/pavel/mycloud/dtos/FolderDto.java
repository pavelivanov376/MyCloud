package com.pavel.mycloud.dtos;

public class FolderDto {
    private int id;
    private String name;
    private String fullPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
