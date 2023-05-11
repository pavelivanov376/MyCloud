package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.services.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/api/folder/{id}")
    public ResponseEntity<Collection<BaseEntityDTO>> getFolderContent(@PathVariable("id") String parenFolderId) {
        return ResponseEntity.ok(folderService.findAllContentByParentFolder(parenFolderId));
    }

    @PostMapping("/api/folder/create")
    public ResponseEntity<String> createFolder(FolderDto folder) {
        folderService.create(folder);

        return ResponseEntity.ok("Folder created");
    }
}
