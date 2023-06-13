package com.pavel.mycloud.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class CompositeFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner")
    private String owner;

    @Column(name = "is_shared")
    private boolean isShared;

    @Column(name = "share_source_uuid")
    private String shareSourceUuid;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public CompositeFileEntity() {
    }

    public CompositeFileEntity(String name, String owner, LocalDateTime creationDate) {
        this.name = name;
        this.owner = owner;
        this.creationDate = creationDate;
    }

    public boolean isShared() {
        return isShared;
    }

    public CompositeFileEntity setShared(boolean shared) {
        isShared = shared;
        return this;
    }

    public String getShareSourceUuid() {
        return shareSourceUuid;
    }

    public CompositeFileEntity setShareSourceUuid(String shareSourceUuid) {
        this.shareSourceUuid = shareSourceUuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompositeFileEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public CompositeFileEntity setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public CompositeFileEntity setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public CompositeFileEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
