package com.example.wellnesstracker.service;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.dto.supplement.CreateSupplementDto;
import com.example.wellnesstracker.dto.supplement.SupplementDto;
import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.repository.SupplementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplementService {

    private final SupplementRepository supplementRepository;

    @Autowired
    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

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
    public SupplementDto updateSupplement(String id, SupplementDto supplementDto) {
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
        return dto;
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

        if (createSupplementDto.getPrice() == null || createSupplementDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must greater than 0");
        }
    }
}