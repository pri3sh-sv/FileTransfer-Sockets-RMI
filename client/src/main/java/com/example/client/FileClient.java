package com.example.client;

import com.example.common.FileTransferProtocol;
import com.example.common.FileMetaDataRegistry;

import java.io.*;
import java.net.Socket;
import java.rmi.Naming;

public class FileClient {
    private static final String FILE_PATH = "C:\\Users\\R.Sailesh\\Downloads\\Unit3 (2).txt";

    public static void main(String[] args) {
        try {
            FileMetaDataRegistry metadataManager = (FileMetaDataRegistry) Naming.lookup("rmi://localhost/FileMetadataManager");

            // Upload file
            File file = new File(FILE_PATH);
            byte[] fileData = new byte[(int) file.length()];
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(fileData);
            }

            FileTransferProtocol fileTransfer = new FileTransferProtocol(file.getName(), fileData);
            uploadFile(fileTransfer);
            System.out.println("File uploaded: " + file.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void uploadFile(FileTransferProtocol fileTransfer) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(fileTransfer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
