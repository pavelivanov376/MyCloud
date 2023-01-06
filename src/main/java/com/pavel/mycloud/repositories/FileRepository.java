package com.pavel.mycloud.repositories;

import com.pavel.mycloud.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByName(String name);
}
