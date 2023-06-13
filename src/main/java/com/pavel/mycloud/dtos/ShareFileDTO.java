package com.pavel.mycloud.dtos;

public class ShareFileDTO {
    private String shareWith;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public ShareFileDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getShareWith() {
        return shareWith;
    }

    public ShareFileDTO setShareWith(String shareWith) {
        this.shareWith = shareWith;
        return this;
    }
}
