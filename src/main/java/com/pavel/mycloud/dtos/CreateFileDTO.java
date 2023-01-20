package com.pavel.mycloud.dtos;

import org.springframework.web.multipart.MultipartFile;

public class CreateFileDTO {

    private String fullPath;
    private MultipartFile content;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public MultipartFile getContent() {
        return content;
    }

//    public void setContent(MultipartFile content) {
//        this.content = content;
//    }
}
