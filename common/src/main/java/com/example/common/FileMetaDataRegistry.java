package com.example.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileMetaDataRegistry extends Remote {
    void lockFile(String filename) throws RemoteException;
    void unlockFile(String filename) throws RemoteException;
    List<String> getVersionHistory() throws RemoteException;
    void updateMetadata(String filename) throws RemoteException;
    boolean isFileLocked(String filename) throws RemoteException;
    MetaDataEntry getMetadata(String filename) throws RemoteException;
}
