package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.CreateFileDTO;
import com.pavel.mycloud.entities.FileEntity;
import com.pavel.mycloud.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
//    private final ModelMapper mapper;


    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
//        this.mapper = mapper;
    }

    @Override
    public void upload(CreateFileDTO fileDTO) {
        //FileEntity fileEntity = mapper.map(fileDTO, FileEntity.class);
        //TODO: return uploadResponse

        FileEntity fileEntity = null;
        try {
            fileEntity = new FileEntity(fileDTO);
        } catch (IOException e) {
            //TODO return failed to upload response
            throw new RuntimeException(e);
        }

        fileRepository.save(fileEntity);
        //TODO return succeeded to upload response
    }

    public void upload(MultipartFile file) {
        FileEntity fileEntity = null;
        try {
            fileEntity = new FileEntity(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity download(Long id) {
        return fileRepository.findById(id).get();
    }
}
