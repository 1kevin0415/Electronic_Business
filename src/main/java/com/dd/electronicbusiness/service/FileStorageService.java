package com.dd.electronicbusiness.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    // 将文件存储路径修改为项目内部的、更可靠的 resources/static/images 目录
    private final Path fileStorageLocation = Paths.get("E:/ecommerce_uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("无法创建用于存储上传文件的目录！", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // 生成一个唯一的文件名，避免重名
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try {
            if(fileName.contains("..")) {
                throw new RuntimeException("文件名中包含无效的路径序列 " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("无法存储文件 " + fileName + "。请再试一次！", ex);
        }
    }
    public org.springframework.core.io.Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("文件未找到 " + fileName);
            }
        } catch (java.net.MalformedURLException ex) {
            throw new RuntimeException("文件未找到 " + fileName, ex);
        }
    }
}