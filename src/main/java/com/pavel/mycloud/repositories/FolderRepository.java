package com.pavel.mycloud.repositories;

import com.pavel.mycloud.entities.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    FolderEntity findByPath(String path);
}
