package com.pavel.mycloud.dtos;

import org.springframework.web.multipart.MultipartFile;

public class CreateFileDTO {

    private String parentFolderId;
    private MultipartFile content;

    private String uuid;

    private String owner;

    public String getUuid() {
        return uuid;
    }

    public CreateFileDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public CreateFileDTO setOwner(String owner) {
        this.owner = owner;
        return this;
    }
}
