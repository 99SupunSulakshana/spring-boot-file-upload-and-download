package com.example.online_file_manager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {
    private static final String STORAGE_DIRECTORY = "D:\\spring boot serious\\online-file-manager";

    public void saveFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile == null){
            throw new NullPointerException("file is empty.");
        }
        var targetFile = new File(STORAGE_DIRECTORY + File.separator + multipartFile.getOriginalFilename());
        if(!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported filename");
        }
        Files.copy(multipartFile.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public File getDownloadFile(String fileName) throws FileNotFoundException {
        if(fileName == null){
            throw new NullPointerException("file name is null");
        }
        var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + fileName);
        if(!Objects.equals(fileToDownload.getParent(), STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported filename");
        }
        if(!fileToDownload.exists()){
            throw new FileNotFoundException("No file named: "+ fileName);
        }
        return fileToDownload;
    }

}
