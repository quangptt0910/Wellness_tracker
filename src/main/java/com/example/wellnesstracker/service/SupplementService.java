package com.example.wellnesstracker.service;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.dto.supplement.CreateSupplementDto;
import com.example.wellnesstracker.dto.supplement.SupplementDto;
import com.example.wellnesstracker.dto.supplement.UpdateSupplementDto;
import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.repository.SupplementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplementService {

    private final SupplementRepository supplementRepository;

    private static final String IMAGE_DIR = "src/main/resources/static/images/supplements/";
    @Autowired
    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    @Transactional
    public SupplementDto createSupplement(@Valid CreateSupplementDto supplementDto) {
       validateCreateSupplementDto(supplementDto);

       //Dto to entity
       Supplement supplement = convertToEntity(supplementDto);

       // Save entity
        Supplement saveSupplement = supplementRepository.save(supplement);

        return convertToDto(saveSupplement);
    }

    public SupplementDto getSupplementById(String id) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with id: " + id));

        return convertToDto(supplement);
    }

    public List<SupplementDto> getAllSupplements() {
        return supplementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SupplementDto> getSupplementsByCategory(Category category) {
        return supplementRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // Update - take DTO, return DTO
    @Transactional
    public SupplementDto updateSupplement(String id, UpdateSupplementDto supplementDto) {
        Supplement existingSupplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with id: " + id));

        // Update fields
        existingSupplement.setName(supplementDto.getName());
        existingSupplement.setCategory(supplementDto.getCategory());
        existingSupplement.setManufacturer(supplementDto.getManufacturer());
        existingSupplement.setBenefits(supplementDto.getBenefits());
        existingSupplement.setDosageAmount(supplementDto.getDosageAmount());
        existingSupplement.setDosageUnit(supplementDto.getDosageUnit());
        existingSupplement.setPrice(supplementDto.getPrice());

        // Save updated entity
        Supplement updatedSupplement = supplementRepository.save(existingSupplement);

        // Return as DTO
        return convertToDto(updatedSupplement);
    }

    @Transactional
    public SupplementDto updateSupplementImage(String id, String imageName) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with id: " + id));

        supplement.setImageName(imageName);
        Supplement updatedSupplement = supplementRepository.save(supplement);

        return convertToDto(updatedSupplement);
    }

    @Transactional
    public SupplementDto removeSupplementImage(String id) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with id: " + id));

        supplement.setImageName(null);
        Supplement updatedSupplement = supplementRepository.save(supplement);

        return convertToDto(updatedSupplement);
    }


    public void deleteSupplement(String id) {
        if (!supplementRepository.existsById(id)) {
            throw new IllegalArgumentException("Supplement not found with id: " + id);
        }
        supplementRepository.deleteById(id);
    }

    public List<SupplementDto> searchSupplementsByName(String name) {
        return supplementRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SupplementDto convertToDto(Supplement supplement) {
        SupplementDto dto = new SupplementDto();
        dto.setId(supplement.getId());
        dto.setName(supplement.getName());
        dto.setCategory(supplement.getCategory());
        dto.setManufacturer(supplement.getManufacturer());
        dto.setBenefits(supplement.getBenefits());
        dto.setDosageAmount(supplement.getDosageAmount());
        dto.setDosageUnit(supplement.getDosageUnit());
        dto.setPrice(supplement.getPrice());

        String detectedImageName = detectImageForSupplement(supplement.getId());
        if (detectedImageName != null) {
            dto.setImageName(detectedImageName);
        } else {
            dto.setImageName(supplement.getImageName());
        }

        return dto;
    }

    private String detectImageForSupplement(String supplementId) {
        Path imageDir = Paths.get(IMAGE_DIR);
        if (!Files.exists(imageDir)) return null;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(imageDir, "supplement-" + supplementId + ".*")) {
            Iterator<Path> iterator = stream.iterator();
            if (iterator.hasNext()) {
                return iterator.next().getFileName().toString();
            }
        } catch (IOException e) {
            // Log error but continue
            System.err.println("Error scanning image directory: " + e.getMessage());
        }
        return null;
    }

    public Supplement convertToEntity(SupplementDto dto) {
        Supplement supplement = new Supplement();
        // Only set the ID if it's not null (for updates)
        if (dto.getId() != null) {
            supplement.setId(dto.getId());
        }
        supplement.setName(dto.getName());
        supplement.setCategory(dto.getCategory());
        supplement.setManufacturer(dto.getManufacturer());
        supplement.setBenefits(dto.getBenefits());
        supplement.setDosageAmount(dto.getDosageAmount());
        supplement.setDosageUnit(dto.getDosageUnit());
        supplement.setPrice(dto.getPrice());
        supplement.setImageName(dto.getImageName());
        return supplement;
    }

    public Supplement convertToEntity(CreateSupplementDto dto) {
        Supplement supplement = new Supplement();
        supplement.setName(dto.getName());
        supplement.setCategory(dto.getCategory());
        supplement.setManufacturer(dto.getManufacturer());
        supplement.setBenefits(dto.getBenefits());
        supplement.setDosageAmount(dto.getDosageAmount());
        supplement.setDosageUnit(dto.getDosageUnit());
        supplement.setPrice(dto.getPrice());
        return supplement;
    }

    private void validateCreateSupplementDto(CreateSupplementDto createSupplementDto) {
        if (createSupplementDto.getName() == null || createSupplementDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (createSupplementDto.getPrice() == null || createSupplementDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must greater than 0");
        }
    }

    public String generateImageName(String supplementId, String originalFileName) {
        String extension = getFileExtension(originalFileName);
        return "supplement-" + supplementId + "." + extension;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg";
        }
        int lastIndex = fileName.lastIndexOf('.');
        return lastIndex > 0 ? fileName.substring(lastIndex + 1).toLowerCase() : "jpg";
    }
}