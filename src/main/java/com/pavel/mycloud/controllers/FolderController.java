package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.CreateFolderDto;
import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.services.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@Controller
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/api/folder/{name}")
    public ResponseEntity<Collection<BaseEntityDTO>> getFolderContent(@PathVariable("name") String filename) {
        return ResponseEntity.ok(folderService.getContent());
    }

    @PostMapping("/api/folder/create")
    public ResponseEntity<String> createFolder(CreateFolderDto folder) {
        folderService.create(folder);

        return ResponseEntity.ok("Folder created");
    }
}
