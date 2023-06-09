package com.pavel.mycloud.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.pavel.mycloud.dtos.CreateFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StorageService {

    private AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    public StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(CreateFileDTO fileDTO) {
        File fileObj = convertMultiPartFileToZipFile(fileDTO.getContent());
        s3Client.putObject(new PutObjectRequest(bucketName, fileDTO.getUuid(), fileObj));
        fileObj.delete();
        return "File uploaded";
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed";
    }

    public byte[] downloadFile(String key, String filename) {
        S3Object s3Object = s3Client.getObject(bucketName, key);
        s3Object.setKey(filename);

        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            //TODO see how to log
        }
        return null;
    }

    private File convertMultiPartFileToZipFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            //TODO see how to log
        }
        return convertedFile;
    }
}
