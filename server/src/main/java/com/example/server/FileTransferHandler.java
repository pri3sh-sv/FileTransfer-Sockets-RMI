package com.example.server;

import com.example.common.FileTransferProtocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferHandler {

    private static final String FILE_STORAGE_DIR = "shared_files/";

    public static void startFileServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)){
            System.out.println("File Server Started...");
            while (true) {
                Socket socket = serverSocket.accept();
                handleClientFile(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientFile(Socket socket) {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){
            FileTransferProtocol fileTransfer = (FileTransferProtocol) inputStream.readObject();
            saveFile(fileTransfer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(FileTransferProtocol fileTransfer) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_STORAGE_DIR + fileTransfer.getFileName())) {
            fileOutputStream.write(fileTransfer.getFileData());
            System.out.println("File Saved to " + fileTransfer.getFileName());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
