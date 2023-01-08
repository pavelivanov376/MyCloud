package com.pavel.mycloud.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "files")
public class FileEntity extends BaseFileEntity {

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private FolderEntity parentFolder;

    @Column(name = "type")
    private String type;

    //@Lob
    //private byte[] content;

    public FolderEntity getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderEntity parentFolder) {
        this.parentFolder = parentFolder;
    }

// Other metadata

//    public FileEntity() {
//    }
//    public FileEntity(CreateFileDTO fileDTO) throws IOException {
//        super(fileDTO.getName(), fileDTO.getPath());
//        this.type = "txt";
//        this.content = fileDTO.getContent().getBytes();
//    }
//
//    public FileEntity(MultipartFile file) throws IOException {
//        super(fileDTO.getName(), fileDTO.getPath());
//        this.type = "txt";
//        this.content = file.getBytes();
//    }


}
