package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.repositories.UserRepository;
import com.pavel.mycloud.services.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
public class FolderController {
    private final FolderService folderService;

    private final UserRepository userRepository;


    public FolderController(FolderService folderService, UserRepository userRepository) {
        this.folderService = folderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/folder/{id}")
    public ResponseEntity<Collection<BaseEntityDTO>> getFolderContent(@PathVariable("id") Long folderId) {
//        return ResponseEntity.ok(folderService.findAllContentByParentFolder(folderId));
        return ResponseEntity.ok(folderService.listById(folderId));
    }

    @GetMapping("/api/home/id")
    public ResponseEntity<String> getHomeFolderContent(Principal principal) {
        Long homeFolderID = userRepository.findByName(principal.getName()).get().getHomeFolderID();
        return ResponseEntity.ok(homeFolderID.toString());
    }

    @PostMapping("/api/folder/create")
    public ResponseEntity<String> createFolder(FolderDto folder, Principal principal) {
        folder.setOwner(principal.getName());
        folderService.create(folder);

        return ResponseEntity.ok("Folder created");
    }
}
