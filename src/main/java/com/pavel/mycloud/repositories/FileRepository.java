package com.pavel.mycloud.repositories;

import com.pavel.mycloud.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByName(String name);

    Collection<FileEntity> findAllByParentFolderId(String parenFolderId);

    FileEntity findByUuid(String uuid);
}
