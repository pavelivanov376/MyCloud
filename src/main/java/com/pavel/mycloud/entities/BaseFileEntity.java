package com.pavel.mycloud.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

//    @ManyToOne
//    @JoinColumn(name = "parent_folder_id")
//    private FolderEntity parentFolder;

    public BaseFileEntity() {
    }

    public BaseFileEntity(String name, String path, String owner, LocalDateTime creationDate) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
