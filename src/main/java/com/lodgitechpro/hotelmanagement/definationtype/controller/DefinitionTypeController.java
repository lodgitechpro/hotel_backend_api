package com.lodgitechpro.hotelmanagement.definationtype.controller;

import com.lodgitechpro.hotelmanagement.definationtype.dto.DefinitionTypeDetailDto;
import com.lodgitechpro.hotelmanagement.definationtype.dto.DefinitionTypeDto;
import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypeDetails;
import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypes;
import com.lodgitechpro.hotelmanagement.definationtype.service.DefinitionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/definitions")
@RequiredArgsConstructor
@Slf4j
public class DefinitionTypeController {

    private final DefinitionTypeService definitionTypeService;

    /**
     * Create or update a DefinitionType.
     */
    @PostMapping("/type")
    public ResponseEntity<DefinitionTypes> saveOrUpdateDefinitionType(@RequestBody DefinitionTypeDto definitionTypeDto) {
        DefinitionTypes savedDefinitionType = definitionTypeService.saveAndUpdateDefinitionType(definitionTypeDto);
        return ResponseEntity.ok(savedDefinitionType);
    }

    /**
     * Create or update a DefinitionTypeDetail.
     */
    @PostMapping("/type-detail")
    public ResponseEntity<DefinitionTypeDetails> saveOrUpdateDefinitionTypeDetail(@RequestBody DefinitionTypeDetailDto definitionTypeDetailDto) {
        DefinitionTypeDetails savedDefinitionTypeDetail = definitionTypeService.saveAndUpdateDefinitionTypeDetail(definitionTypeDetailDto);
        return ResponseEntity.ok(savedDefinitionTypeDetail);
    }

    /**
     * Get a DefinitionType by ID.
     */
    @GetMapping("/type/{id}")
    public ResponseEntity<DefinitionTypes> getDefinitionTypeById(@PathVariable Integer id) {
        DefinitionTypes definitionType = definitionTypeService.getDefinitionTypeById(id);
        return ResponseEntity.ok(definitionType);
    }

    /**
     * Get a DefinitionTypeDetail by ID.
     */
    @GetMapping("/type-detail/{id}")
    public ResponseEntity<DefinitionTypeDetails> getDefinitionTypeDetailById(@PathVariable Integer id) {
        DefinitionTypeDetails definitionTypeDetail = definitionTypeService.getDefinitionTypeDetailById(id);
        return ResponseEntity.ok(definitionTypeDetail);
    }

    /**
     * Get all DefinitionTypes.
     */
    @GetMapping("/types")
    public ResponseEntity<List<DefinitionTypes>> getAllDefinitionTypes() {
        List<DefinitionTypes> definitionTypes = definitionTypeService.getAllDefinitionTypes();
        return ResponseEntity.ok(definitionTypes);
    }

    /**
     * Get all DefinitionTypeDetails.
     */
    @GetMapping("/type/{id}/details")
    public ResponseEntity<List<DefinitionTypeDetails>> getAllDefinitionTypeDetails(@PathVariable int id) {
        List<DefinitionTypeDetails> definitionTypeDetails = definitionTypeService.getDetailsByDefinitionTypeId(id);
        return ResponseEntity.ok(definitionTypeDetails);
    }

    /**
     * Get all DefinitionTypeDetails.
     */
    @GetMapping("/type/details/{id}")
    public ResponseEntity<List<DefinitionTypeDetails>> getAllDefinitionTypeDetailsById(@PathVariable int id) {
        List<DefinitionTypeDetails> definitionTypeDetails = definitionTypeService.getDetailsByDefinitionTypeId(id);
        return ResponseEntity.ok(definitionTypeDetails);
    }

    /**
     * Delete a DefinitionType by ID.
     */
    @DeleteMapping("/type/{id}")
    public ResponseEntity<Void> deleteDefinitionType(@PathVariable Integer id) {
        definitionTypeService.deleteDefinitionType(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a DefinitionTypeDetail by ID.
     */
    @DeleteMapping("/type-detail/{id}")
    public ResponseEntity<Void> deleteDefinitionTypeDetail(@PathVariable Integer id) {
        definitionTypeService.deleteDefinitionTypeDetail(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search for DefinitionTypes dynamically.
     */
    @PostMapping("/type/search")
    public ResponseEntity<List<DefinitionTypes>> searchDefinitionTypes(@RequestBody DefinitionTypeDto definitionTypeDto) {
        List<DefinitionTypes> result = definitionTypeService.searchDefinitionTypes(definitionTypeDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Search for DefinitionTypeDetails dynamically.
     */
    @PostMapping("/type-detail/search")
    public ResponseEntity<List<DefinitionTypeDetails>> searchDefinitionTypeDetails(@RequestBody DefinitionTypeDetailDto definitionTypeDetailDto) {
        List<DefinitionTypeDetails> result = definitionTypeService.searchDefinitionTypeDetails(definitionTypeDetailDto);
        return ResponseEntity.ok(result);
    }

}
