package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.CreateFolderDto;
import com.pavel.mycloud.services.FolderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FolderController {
    public final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/createFolder")
    public String uploadFile(CreateFolderDto folder, RedirectAttributes attributes) {
        if (folder == null || folder.getFullPath() == null || folder.getFullPath().isEmpty()) {
            attributes.addFlashAttribute("messageCreateFolder", "Unable to create the folder");
            return "redirect:/";
        }

        folderService.create(folder);

        attributes.addFlashAttribute("messageCreateFolder", "Successfully uploaded");

        return "redirect:/";
    }
}
