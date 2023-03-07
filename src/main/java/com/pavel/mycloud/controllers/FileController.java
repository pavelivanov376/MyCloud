package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.DownloadFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Controller
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/createFile")
    public String uploadFile(CreateFileDTO file, RedirectAttributes attributes) {
        if (file.getContent() == null || file.getContent().isEmpty()) {
            attributes.addFlashAttribute("messageCreateFile", "Unable to upload the file");
            return "redirect:/";
        }

        fileService.upload(file);

        attributes.addFlashAttribute("messageCreateFile", "Successfully uploaded");

        return "redirect:/";
    }

    @GetMapping("/downloadFile")
    public String downloadFile(DownloadFileDTO fileDTO, RedirectAttributes attributes, HttpServletResponse response) throws IOException {
        FileEntity file = fileService.download(fileDTO.getName());
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + file.getName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(file.getContent());
        outputStream.close();

        attributes.addFlashAttribute("messageDownload", "Successfully downloaded");

        return "redirect:/";
    }

//    @GetMapping(value = "/listAllFiles", produces = MediaType.TEXT_HTML_VALUE)
//    public String listAllFiles(DownloadFileDTO fileDTO, RedirectAttributes attributes, HttpServletResponse response) {
//        Collection<FileEntity> list =  fileService.getAllFiles();
//
//        return list.toString();
//    }
}
