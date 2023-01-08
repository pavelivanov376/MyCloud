package com.pavel.mycloud.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "folders")
public class FolderEntity extends BaseFileEntity {
    @OneToMany(mappedBy = "parentFolder",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<FileEntity> content;

    public FolderEntity() {
    }

    public FolderEntity(String name, String path, String owner, LocalDateTime creationDate) {
        super(name, path, owner, creationDate);
    }


}
