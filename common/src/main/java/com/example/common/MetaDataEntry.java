package com.example.common;

import java.io.Serializable;
import java.util.Date;

public class MetaDataEntry implements Serializable {

    private String fileName;
    private Long fileSize;
    private Date lastModified;
    private boolean isLocked;
    private int version;

    public MetaDataEntry(String fileName, Long fileSize, Date lastModified, boolean isLocked, int version) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
        this.isLocked = isLocked;
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getLastModified() {
        return lastModified;
    }
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public void incrementVersion() {
        this.version++;
        this.lastModified = new Date();
    }

    @Override
    public String toString() {
        return "MetadataEntry{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", lastModified=" + lastModified +
                ", version=" + version +
                ", isLocked=" + isLocked +
                '}';
    }
}
