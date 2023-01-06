package com.pavel.mycloud.entities;

import com.pavel.mycloud.dtos.CreateFileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

//    private FolderEntity parentFolder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    // Other metadata
    @Lob
    private byte[] content;

    public FileEntity() {
    }

    public FileEntity(CreateFileDTO fileDTO) throws IOException {
        this.name = fileDTO.getName();
        this.type = "txt";
        this.content = fileDTO.getContent().getBytes();
    }

    public FileEntity(MultipartFile file) throws IOException {
        this.name = file.getOriginalFilename();
        this.type = "txt";
        this.content = file.getBytes();
    }
}
