package com.example.wellnesstracker.controller;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/supplements/";


    @GetMapping("/supplements/{imageName}")
    public ResponseEntity<Resource> getSupplementImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR).resolve(imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "image/jpeg";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                // Return default placeholder image
                return getPlaceholderImage();
            }
        } catch (Exception e) {
            return getPlaceholderImage();
        }
    }

    private ResponseEntity<Resource> getPlaceholderImage() {
        try {
            Path placeholderPath = Paths.get("src/main/resources/static/images/supplement-placeholder.jpg");
            Resource resource = new UrlResource(placeholderPath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
