package com.pavel.mycloud.controllers;

import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.services.FileService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String home() {
        return "files/uploadFile.html";
    }

    @GetMapping("/upload")
    public String uploadFile() {
        return "files/uploadFile.html";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        fileService.upload(file);

        attributes.addFlashAttribute("message", "You successfully uploaded a file");

        return "redirect:files/uploadFile.html";
    }

    @GetMapping("/download")
    public String downloadFile(@Param("id") Long id, Model model, HttpServletResponse response) throws IOException {
        FileEntity file = fileService.download(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + file.getName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(file.getContent());
        outputStream.close();
        return "redirect:/";
    }
}
