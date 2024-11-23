package com.example.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.example.common.FileMetaDataRegistry;
import com.example.common.MetaDataEntry;

public class FileMetaDataManagerImpl extends UnicastRemoteObject implements FileMetaDataRegistry {
    private final ConcurrentHashMap<String, Boolean> fileLocks = new ConcurrentHashMap<>();
    private final List<String> versionHistory = new ArrayList<>();

    protected FileMetaDataManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public void lockFile(String fileName) throws RemoteException {
        fileLocks.put(fileName, true);
        System.out.println("File locked: " + fileName);
    }

    @Override
    public void unlockFile(String fileName) throws RemoteException {
        fileLocks.remove(fileName);
        System.out.println("File unlocked: " + fileName);
    }

    @Override
    public List<String> getVersionHistory() throws RemoteException {
        return new ArrayList<>(versionHistory);
    }

    @Override
    public void updateMetadata(String fileName) throws RemoteException {
        versionHistory.add("Updated file: " + fileName);
        System.out.println("Metadata updated for file: " + fileName);
    }

    @Override
    public boolean isFileLocked(String fileName) throws RemoteException {
        return fileLocks.getOrDefault(fileName, false);
    }

    @Override
    public MetaDataEntry getMetadata(String filename) throws RemoteException {
        return null;
    }

}
