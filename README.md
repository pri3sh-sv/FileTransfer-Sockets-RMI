### README: File Collaborative System

---

## File Collaborative System

The **File Collaborative System** is a distributed application that enables efficient file transfer and metadata management using **Socket Programming** and **Java RMI**. The system consists of three modules: `common`, `client`, and `server`. File transfers occur through socket communication, where clients can upload files to the server using absolute file paths. Metadata operations such as locking files, updating metadata, and retrieving version history are managed via RMI. The system is designed to ensure collaborative file usage while maintaining file state consistency through metadata management.

---

## Prerequisites

- **Java Version:** 23
- **Maven** installed on your system.

---

## Installation

1. Clone the repository or download the source code.
2. Navigate to the root directory of the project.
3. Build the project using the following command:
   ```bash
   mvn clean install
   ```
   This will generate three JAR files:
    - `common.jar`: Contains shared interfaces and protocols.
    - `server.jar`: Contains the server-side logic, including RMI implementation.
    - `client.jar`: Contains the client-side logic, including UI and socket operations.

---

## Running the Application

1. **Start the Server:**
   Run the `server.jar` to start the server. This will initialize the RMI registry and socket server.
   ```bash
   java -jar server/target/server.jar
   ```

2. **Start the Client:**
   Run the `client.jar` to launch the client. Multiple instances of the client can be run to simulate collaborative usage.
   ```bash
   java -jar client/target/client.jar
   ```

3. **Perform File Operations:**
    - When prompted, provide the **absolute path** of the file for transfer during socket communication.
    - Use the menu-driven options for metadata operations:
        - **Lock/Unlock a File**
        - **Update Metadata**
        - **Retrieve Version History**
        - **Check Lock Status**
        - **Add New Metadata**

---

## RMI Details

The application uses **Java RMI (Remote Method Invocation)** for managing file metadata. Below is a summary of the RMI methods implemented:

1. **`lockFile(String filename)`**  
   Locks a file for exclusive access and updates the metadata state.

2. **`unlockFile(String filename)`**  
   Unlocks a file, making it available for others to access or modify.

3. **`getVersionHistory()`**  
   Returns a list of version identifiers for a file, helping track its changes over time.

4. **`updateMetadata(String filename)`**  
   Updates metadata for a specified file with new information.

5. **`isFileLocked(String filename)`**  
   Checks whether a file is currently locked.

6. **`getMetadata(String filename)`**  
   Retrieves metadata details such as file size, version, last modified time, and lock status.

7. **`addFileMetaData(String fileName, MetaDataEntry metaDataEntry)`**  
   Adds new metadata for a specified file to the system.

---

## Additional Notes

- **RMI Registry**: The RMI registry is bundled in the `common.jar` and shared between the `client` and `server`.
- **Socket Programming**: Handles file transfers from client to server, ensuring robust and secure communication.
- **Absolute File Paths**: Ensure you provide the full path for files during transfers (e.g., `/home/user/file.txt` on Linux or `C:\Users\User\file.txt` on Windows).

---
