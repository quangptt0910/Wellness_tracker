package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.dto.supplement.CreateSupplementDto;
import com.example.wellnesstracker.dto.supplement.SupplementDto;
import com.example.wellnesstracker.dto.supplement.UpdateSupplementDto;
import com.example.wellnesstracker.service.ImageService;
import com.example.wellnesstracker.service.SupplementService;
import com.example.wellnesstracker.common.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/supplements")
public class AdminSupplementController {

    @Autowired
    private SupplementService supplementService;

    @Autowired
    private ImageService imageService;

    // Get all supplements
    @GetMapping
    public ResponseEntity<List<SupplementDto>> getAllSupplements() {
        List<SupplementDto> supplements = supplementService.getAllSupplements();
        return ResponseEntity.ok(supplements);
    }

    // Get supplement by ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplementDto> getSupplementById(@PathVariable String id) {
        try {
            SupplementDto supplement = supplementService.getSupplementById(id);
            return ResponseEntity.ok(supplement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create supplement
    @PostMapping
    public ResponseEntity<SupplementDto> createSupplement(@Valid @RequestBody CreateSupplementDto createDto) {
        try {
            SupplementDto created = supplementService.createSupplement(createDto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update supplement
    @PutMapping("/{id}")
    public ResponseEntity<SupplementDto> updateSupplement(
            @PathVariable String id,
            @RequestBody UpdateSupplementDto updateDto) {
        try {
            SupplementDto updated = supplementService.updateSupplement(id, updateDto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete supplement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplement(@PathVariable String id) {
        try {
            // Get supplement to check if it has an image
            SupplementDto supplement = supplementService.getSupplementById(id);

            // Delete associated image if exists
            if (supplement.hasImage()) {
                imageService.deleteSupplementImage(id);
            }

            // Delete supplement
            supplementService.deleteSupplement(id);

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Upload/Update image for supplement
    @PostMapping("/{id}/image")
    public ResponseEntity<Map<String, String>> uploadSupplementImage(
            @PathVariable String id,
            @RequestParam("image") MultipartFile file) {

        try {
            // Check if supplement exists (this will throw exception if not found)
            supplementService.getSupplementById(id);

            // Save image file
            String imageName = imageService.saveSupplementImage(file, id);

            // Update supplement with image name
            SupplementDto updated = supplementService.updateSupplementImage(id, imageName);

            Map<String, String> response = new HashMap<>();
            response.put("imageName", imageName);
            response.put("imageUrl", updated.getImageUrl());
            response.put("message", "Image uploaded successfully");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to upload image: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    // Delete image for supplement
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Map<String, String>> deleteSupplementImage(@PathVariable String id) {
        try {
            // Get supplement to check if it exists and has an image
            SupplementDto supplement = supplementService.getSupplementById(id);

            if (supplement.hasImage()) {
                // Delete the physical file
                imageService.deleteSupplementImage(id);

                // Remove image reference from supplement
                supplementService.removeSupplementImage(id);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Image deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No image to delete");
                return ResponseEntity.ok(response);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete image: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    // Get supplements by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SupplementDto>> getSupplementsByCategory(@PathVariable Category category) {
        List<SupplementDto> supplements = supplementService.getSupplementsByCategory(category);
        return ResponseEntity.ok(supplements);
    }

    // Search supplements by name
    @GetMapping("/search")
    public ResponseEntity<List<SupplementDto>> searchSupplements(@RequestParam String name) {
        List<SupplementDto> supplements = supplementService.searchSupplementsByName(name);
        return ResponseEntity.ok(supplements);
    }
}