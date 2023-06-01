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

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public CompositeFileEntity() {
    }

    public CompositeFileEntity(String name, String owner, LocalDateTime creationDate) {
        this.name = name;
        this.owner = owner;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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
