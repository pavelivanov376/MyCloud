package com.pavel.mycloud.dtos;

public class ShareDTO {
    private String shareWith;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public ShareDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getShareWith() {
        return shareWith;
    }

    public ShareDTO setShareWith(String shareWith) {
        this.shareWith = shareWith;
        return this;
    }
}
