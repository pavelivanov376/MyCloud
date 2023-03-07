package com.pavel.mycloud.controllers;

import com.pavel.mycloud.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/")
    public String home(RedirectAttributes attributes) {
//        Collection<FileEntity> list = fileService.getAllFiles();
//        attributes.addFlashAttribute("listOfAllFiles", list.toString());

        return "files/index.html";
    }
}
