package com.pavel.mycloud.dtos;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

public class CreateFileDTO {

    private String name;
    private MultipartFile content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile content) {
        this.content = content;
    }
}
