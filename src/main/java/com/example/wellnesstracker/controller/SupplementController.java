package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.dto.supplement.CreateSupplementDto;
import com.example.wellnesstracker.dto.supplement.SupplementDto;
import com.example.wellnesstracker.service.SupplementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/supplement")
public class SupplementController {

    private final SupplementService supplementService;

    @Autowired
    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    @PostMapping
    public ResponseEntity<SupplementDto> create(@Valid @RequestBody CreateSupplementDto createSupplementDto) {
        SupplementDto createdSupplement = supplementService.createSupplement(createSupplementDto);
        return new ResponseEntity<>(createdSupplement, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SupplementDto> getSupplementById(@PathVariable String id) {
        SupplementDto supplement = supplementService.getSupplementById(id);
        return ResponseEntity.ok(supplement);
    }

    @GetMapping
    public ResponseEntity<List<SupplementDto>> getAllSupplements() {
        List<SupplementDto> supplements = supplementService.getAllSupplements();
        return ResponseEntity.ok(supplements);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SupplementDto>> getSupplementsByCategory(@PathVariable Category category) {
        List<SupplementDto> supplements = supplementService.getSupplementsByCategory(category);
        return ResponseEntity.ok(supplements);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplementDto> updateSupplement(@PathVariable String id, @Valid @RequestBody SupplementDto supplementDto) {
        SupplementDto updatedSupplement = supplementService.updateSupplement(id, supplementDto);
        return ResponseEntity.ok(updatedSupplement);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SupplementDto>> searchSupplements(@RequestParam String name) {
        List<SupplementDto> supplements = supplementService.searchSupplementsByName(name);
        return ResponseEntity.ok(supplements);
    }

    //Exception handler for validation errors
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
//        ErrorResponse error = new ErrorResponse("BAD_REQUEST", ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}