package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Collection;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //TODO Create a new endpoint where we return not stream directly but dto, which is built in the service so here we just receive it
    @GetMapping("/api/download/{uuid}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("uuid") String uuid) throws IOException {
        FileEntity file = fileService.findByUuid(uuid);
        InputStream stream = new ByteArrayInputStream(file.getContent());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @PostMapping("/api/upload") //    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
    public ResponseEntity<String> uploadFile(CreateFileDTO file, Principal principal) throws IOException {
        if (file.getContent() == null || file.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("Empty File cannot be upload");
        }

        file.setOwner(principal.getName());
        fileService.upload(file);
        return ResponseEntity.ok()
                .body("File uploaded");
    }
}
