package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.dtos.DownloadFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/files")
    public ResponseEntity<Collection<DownloadFileDTO>> findAllFiles() {
        return ResponseEntity.ok(fileService.findAllFiles());
    }

    @GetMapping("/api/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("filename") String filename) throws IOException {
        FileEntity file = fileService.download(filename);
        InputStream stream = new ByteArrayInputStream(file.getContent());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @Deprecated(since = "Not using Thymeleaf")
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

    @Deprecated(since = "Not using Thymeleaf")
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
}
