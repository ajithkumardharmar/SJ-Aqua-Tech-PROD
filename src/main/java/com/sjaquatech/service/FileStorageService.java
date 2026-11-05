package com.sjaquatech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please select an image");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        Path uploadPath = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        Files.createDirectories(uploadPath);

        String extension = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new IllegalArgumentException(
                    "Only JPG, PNG and WEBP images are allowed");
        };

        String fileName = UUID.randomUUID() + extension;

        Path target = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), target,
                StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }
}