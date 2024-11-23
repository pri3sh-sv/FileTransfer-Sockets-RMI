package com.example.common;

import java.io.Serializable;

public class FileTransferProtocol implements Serializable {
    private String fileName;
    private byte[] fileData;

    public FileTransferProtocol(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }
    public byte[] getFileData() {
        return fileData;
    }
}
