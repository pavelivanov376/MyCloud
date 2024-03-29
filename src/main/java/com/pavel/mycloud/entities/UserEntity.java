package com.pavel.mycloud.entities;

import javax.persistence.*;

@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private UserRole role;

    @Column(name = "home_folder_uuid")
    private String homeFolderUuid;

    @Column(name = "share_folder_uuid")
    private String shareFolderUuid;

    public String getShareFolderUuid() {
        return shareFolderUuid;
    }

    public UserEntity setShareFolderUuid(String shareFolderUuid) {
        this.shareFolderUuid = shareFolderUuid;
        return this;
    }

    public String getHomeFolderUuid() {
        return homeFolderUuid;
    }

    public UserEntity setHomeFolderUuid(String homeFolderUuid) {
        this.homeFolderUuid = homeFolderUuid;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public UserEntity setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
