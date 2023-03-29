package com.pavel.mycloud.entities;

import javax.persistence.*;

@Entity(name = "files")
public class FileEntity extends BaseFileEntity {

    @ManyToOne
    private FolderEntity parentFolder;

    @Column(name = "type")
    private String type;

    @Transient
    private byte[] content;

    public FolderEntity getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderEntity parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
