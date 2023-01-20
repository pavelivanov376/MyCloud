package com.pavel.mycloud.dtos;

public class CreateFolderDto {
    private String fullPath;

    public String getFullPath() {
        return fullPath;
    }

    public String getFullPathWithoutFolderName() {
        return fullPath.substring(0, fullPath.lastIndexOf('/'));
    }

    public String getFolderName() {
        return fullPath.substring(fullPath.lastIndexOf('/') + 1);
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
