package com.pavel.mycloud.entities;

import com.pavel.mycloud.dtos.CreateFileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.io.IOException;

@Entity(name = "files")
public class FileEntity extends BaseFileEntity {

    @ManyToOne
    private FolderEntity parentFolder;

    @Column(name = "type")
    private String type;

    @Lob
    private byte[] content;

    public FileEntity() {

    }
//    public FileEntity(CreateFileDTO fileDTO) throws IOException {
////        super(fileDTO.getName(), fileDTO.getPath());
//        this.type = "txt";
//        this.content = fileDTO.getContent().getBytes();
//    }

    public FileEntity(MultipartFile file) throws IOException {
//        super(fileDTO.getName(), fileDTO.getPath());
        this.type = "txt";
        this.content = file.getBytes();
    }


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
