package com.pavel.mycloud.repositories;

import com.pavel.mycloud.entities.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    FolderEntity findByName(String name);

    Collection<FolderEntity> findAllByParentFolderId(String parenFolderId);
}
