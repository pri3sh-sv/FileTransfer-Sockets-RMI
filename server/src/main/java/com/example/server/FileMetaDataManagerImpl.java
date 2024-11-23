package com.example.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.example.common.FileMetaDataRegistry;
import com.example.common.MetaDataEntry;

public class FileMetaDataManagerImpl extends UnicastRemoteObject implements FileMetaDataRegistry {
    private final ConcurrentHashMap<String, MetaDataEntry> fileMetaData = new ConcurrentHashMap<>();
    private final List<String> versionHistory = new ArrayList<>();

    protected FileMetaDataManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public void lockFile(String fileName) throws RemoteException {
        MetaDataEntry metaDataEntry = fileMetaData.get(fileName);
        if (metaDataEntry == null) {
            System.out.println("Meta Data for file name not present");
        }
        metaDataEntry.setLocked(true);
        System.out.println("File locked: " + fileName);
    }

    @Override
    public void unlockFile(String fileName) throws RemoteException {
        MetaDataEntry metaDataEntry = fileMetaData.get(fileName);
        if (metaDataEntry == null) {
            System.out.println("Meta Data for file name not present");
        }
        metaDataEntry.setLocked(false);
        System.out.println("File unlocked: " + fileName);
    }

    @Override
    public List<String> getVersionHistory() throws RemoteException {
        return new ArrayList<>(versionHistory);
    }

    @Override
    public void updateMetadata(String fileName) throws RemoteException {
        MetaDataEntry metaDataEntry = fileMetaData.get(fileName);
        if (metaDataEntry != null) {
            metaDataEntry.incrementVersion();
            metaDataEntry.setLastModified(new Date());
            versionHistory.add("Updated file: " + fileName + " to version " + metaDataEntry.getVersion());
            System.out.println("Metadata updated for file: " + fileName);
        } else {
            System.out.println("Metadata for file " + fileName + " not found.");
        }
    }

    @Override
    public boolean isFileLocked(String fileName) throws RemoteException {
        MetaDataEntry metaDataEntry = fileMetaData.get(fileName);
        return metaDataEntry.isLocked();
    }

    @Override
    public MetaDataEntry getMetadata(String filename) throws RemoteException {
        return fileMetaData.get(filename);
    }

    @Override
    public void addFileMetaData(String fileName, MetaDataEntry metaDataEntry) throws RemoteException {
        fileMetaData.put(fileName, metaDataEntry);
        versionHistory.add("Created file: " + fileName + " with initial version.");

    }
}
