package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.BaseEntityDTO;
import com.pavel.mycloud.dtos.FolderDto;
import com.pavel.mycloud.dtos.ShareDTO;
import com.pavel.mycloud.repositories.UserRepository;
import com.pavel.mycloud.services.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
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

    @GetMapping("/api/folder/{uuid}")
    public ResponseEntity<Collection<BaseEntityDTO>> getFolderContent(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(folderService.listByUuid(uuid));
    }

    @GetMapping("/api/home/id")
    public ResponseEntity<String> getHomeFolderContent(Principal principal) {
        String homeFolderID = userRepository.findByName(principal.getName()).get().getHomeFolderUuid();
        return ResponseEntity.ok(homeFolderID);
    }

    @PostMapping("/api/folder/create")
    public ResponseEntity<String> createFolder(FolderDto folder, Principal principal) {
        folder.setOwner(principal.getName());
        folderService.create(folder);

        return ResponseEntity.ok("Folder created");
    }

    @DeleteMapping("/api/folder/{uuid}")
    public ResponseEntity<String> deleteFolder(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(folderService.delete(uuid));
    }

    @PostMapping("/api/folder/share")
    public ResponseEntity<String> shareFile(ShareDTO shareDTO) throws IOException {
        return ResponseEntity.ok(folderService.share(shareDTO));
    }
}
