package com.pavel.mycloud.dtos;

public class FolderDto {
    private String fullPath;
    private String name;

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
}
