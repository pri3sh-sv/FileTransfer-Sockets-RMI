package com.example.client;

import com.example.common.FileTransferProtocol;
import com.example.common.FileMetaDataRegistry;
import com.example.common.MetaDataEntry;

import java.io.*;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileClient {
//    private static final String FILE_PATH = "C:\\Users\\R.Sailesh\\Downloads\\Unit3 (2).txt";

    public static void main(String[] args) {
        try {
            FileMetaDataRegistry metadataManager = (FileMetaDataRegistry) Naming.lookup("rmi://localhost/FileMetadataManager");

            Scanner scanner = new Scanner(System.in);
            boolean stop = false;

            while (!stop) {
                System.out.println("\nFile Metadata Manager Menu");
                System.out.println("1. Lock File");
                System.out.println("2. Unlock File");
                System.out.println("3. Update Metadata");
                System.out.println("4. Get Version History");
                System.out.println("5. Check If File is Locked");
                System.out.println("6. Get File Metadata");
                System.out.println("7. Add New File Metadata");
                System.out.println("8. Upload File");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter file name to lock: ");
                        String lockFileName = scanner.nextLine();
                        metadataManager.lockFile(lockFileName);
                        break;

                    case 2:
                        System.out.print("Enter file name to unlock: ");
                        String unlockFileName = scanner.nextLine();
                        metadataManager.unlockFile(unlockFileName);
                        break;

                    case 3:
                        System.out.print("Enter file name to update metadata: ");
                        String updateFileName = scanner.nextLine();
                        metadataManager.updateMetadata(updateFileName);
                        break;

                    case 4:
                        getVersionHistory(metadataManager);
                        break;

                    case 5:
                        System.out.print("Enter file name to check if locked: ");
                        String checkFileName = scanner.nextLine();
                        checkIfFileLocked(metadataManager,checkFileName);
                        break;

                    case 6:
                        System.out.print("Enter file name to get metadata: ");
                        String fileMetadataName = scanner.nextLine();
                        getFileMetadata(metadataManager, fileMetadataName);
                        break;

                    case 7:
                        addNewFileMetadata(metadataManager, scanner);
                        break;

                    case 8:
                        uploadFile(scanner);
                        break;

                    case 9:
                        stop = true;
                        System.out.println("Exiting the program.");
                        break;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getVersionHistory(FileMetaDataRegistry metadataManager) {
        try {
            System.out.println("Version History:");
            for (String version : metadataManager.getVersionHistory()) {
                System.out.println(version);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkIfFileLocked(FileMetaDataRegistry metadataManager, String fileName) {
        try {
            boolean isLocked = metadataManager.isFileLocked(fileName);
            System.out.println("Is the file " + fileName + " locked? " + (isLocked ? "Yes" : "No"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getFileMetadata(FileMetaDataRegistry metadataManager, String fileName) {
        try {
            MetaDataEntry metadata = metadataManager.getMetadata(fileName);
            if (metadata != null) {
                System.out.println("File Metadata: " + metadata);
            } else {
                System.out.println("No metadata found for file: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addNewFileMetadata(FileMetaDataRegistry metadataManager, Scanner scanner) {
        try {
            System.out.print("Enter new file name: ");
            String newFileName = scanner.nextLine();
            System.out.print("Enter file size: ");
            long fileSize = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter last modified date (YYYY-MM-DD): ");
            String lastModifiedDate = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date lastModified = dateFormat.parse(lastModifiedDate);
            System.out.print("Is the file locked (true/false): ");
            boolean isLocked = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Enter initial version: ");
            int version = scanner.nextInt();
            scanner.nextLine();

            MetaDataEntry newMetadata = new MetaDataEntry(newFileName, fileSize, lastModified, isLocked, version);
            metadataManager.addFileMetaData(newFileName, newMetadata);
            System.out.println("File metadata added for " + newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void uploadFile(Scanner scanner) {
        try {
            System.out.println("Enter file name: ");
            String FILE_PATH = scanner.nextLine();
            File file = new File(FILE_PATH);
            byte[] fileData = new byte[(int) file.length()];
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(fileData);
            }

            FileTransferProtocol fileTransfer = new FileTransferProtocol(file.getName(), fileData);
            uploadFileToServer(fileTransfer);
            System.out.println("File uploaded: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uploadFileToServer(FileTransferProtocol fileTransfer) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(fileTransfer);
            outputStream.flush();
            System.out.println("File transfer completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}