package com.example.server;

import com.example.common.FileMetaDataRegistry;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class FileServer {

    private static final String FILE_STORAGE_DIR = "shared_files/";

    public static void main(String[] args) {

        File storageDir = new File(FILE_STORAGE_DIR);
        if (!storageDir.exists()) {
            boolean created = storageDir.mkdir();
            if (created) {
                System.out.println("Directory created: " + FILE_STORAGE_DIR);
            } else {
                System.err.println("Failed to create directory: " + FILE_STORAGE_DIR);
            }
        } else {
            System.out.println("Directory already exists: " + FILE_STORAGE_DIR);
        }

        try{
            FileMetaDataRegistry fileMetaDataRegistry = new FileMetaDataManagerImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("FileMetadataManager", fileMetaDataRegistry);
            System.out.println("RMI Server is running...");

            FileTransferHandler.startFileServer();
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
