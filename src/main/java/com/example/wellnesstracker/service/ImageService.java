package com.example.wellnesstracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/supplements/";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public String saveSupplementImage(MultipartFile file, String supplementId) throws IOException {
        // Validate file
        validateImageFile(file);

        // Create directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate filename
        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = "supplement-" + supplementId + "." + extension;

        // Delete old image if exists
        deleteOldImage(supplementId);

        // Save new image
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public void deleteSupplementImage(String supplementId) {
        deleteOldImage(supplementId);
    }

    private void deleteOldImage(String supplementId) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.walk(uploadPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("supplement-" + supplementId + "."))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Log error but don't throw
                            System.err.println("Failed to delete old image: " + path);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error while searching for old images: " + e.getMessage());
        }
    }

    private void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum limit");
        }

        String extension = getFileExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Invalid file type. Allowed: " + ALLOWED_EXTENSIONS);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg";
        }
        int lastIndex = fileName.lastIndexOf('.');
        return lastIndex > 0 ? fileName.substring(lastIndex + 1).toLowerCase() : "jpg";
    }
}
