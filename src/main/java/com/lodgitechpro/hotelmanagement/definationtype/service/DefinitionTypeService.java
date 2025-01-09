package com.lodgitechpro.hotelmanagement.definationtype.service;

import com.lodgitechpro.hotelmanagement.definationtype.dto.DefinitionTypeDetailDto;
import com.lodgitechpro.hotelmanagement.definationtype.dto.DefinitionTypeDto;
import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypeDetails;
import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypes;
import com.lodgitechpro.hotelmanagement.definationtype.repository.DefinitionTypeDetailsRepository;
import com.lodgitechpro.hotelmanagement.definationtype.repository.DefinitionTypesRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefinitionTypeService {

    private final DefinitionTypesRepository definitionTypeRepository;
    private final DefinitionTypeDetailsRepository definitionTypeDetailRepository;
    private final EntityMapper entityMapper;

    // ----- DefinitionType Methods -----

    /**
     * Save or update a definition type.
     * If the ID is provided in DefinitionTypeDto, it updates the definition type; otherwise, it inserts a new record.
     */
    public DefinitionTypes saveAndUpdateDefinitionType(DefinitionTypeDto definitionTypeDto) {
        DefinitionTypes definitionType = entityMapper.mapDtoToEntity(definitionTypeDto, DefinitionTypes.class);
        definitionType = definitionTypeRepository.save(definitionType);
        return definitionType;
    }

    /**
     * Get a definition type by ID.
     */
    public DefinitionTypes getDefinitionTypeById(Integer id) {
        return definitionTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DefinitionType with ID " + id + " not found."));
    }

    /**
     * Get all definition types.
     */
    public List<DefinitionTypes> getAllDefinitionTypes() {
        return definitionTypeRepository.findAll();
    }

    /**
     * Delete a definition type by ID.
     */
    public void deleteDefinitionType(Integer id) {
        if (!definitionTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("DefinitionType with ID " + id + " not found.");
        }
        definitionTypeRepository.deleteById(id);
    }

    // ----- DefinitionTypeDetail Methods -----

    /**
     * Save or update a definition type detail.
     * If the ID is provided in DefinitionTypeDetailDto, it updates the record; otherwise, it inserts a new one.
     */
    public DefinitionTypeDetails saveAndUpdateDefinitionTypeDetail(DefinitionTypeDetailDto definitionTypeDetailDto) {
        // Validate the parent definition type exists
        Integer definitionTypeId = definitionTypeDetailDto.getDefinitionTypeId();
        if (!definitionTypeRepository.existsById(definitionTypeId)) {
            throw new EntityNotFoundException("DefinitionType with ID " + definitionTypeId + " not found.");
        }

        DefinitionTypeDetails definitionTypeDetail = entityMapper.mapDtoToEntity(definitionTypeDetailDto, DefinitionTypeDetails.class);
        DefinitionTypes definitionType = getDefinitionTypeById(definitionTypeDetailDto.getDefinitionTypeId());
        definitionTypeDetail.setDefinitionType(definitionType);
        definitionTypeDetail = definitionTypeDetailRepository.save(definitionTypeDetail);
        return definitionTypeDetail;
    }

    /**
     * Get a definition type detail by ID.
     */
    public DefinitionTypeDetails getDefinitionTypeDetailById(Integer id) {
        return definitionTypeDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DefinitionTypeDetail with ID " + id + " not found."));
    }

    /**
     * Get all definition type details for a specific definition type.
     */
    public List<DefinitionTypeDetails> getDetailsByDefinitionTypeId(Integer definitionTypeId) {
        DefinitionTypes definitionTypeById = getDefinitionTypeById(definitionTypeId);
        return definitionTypeDetailRepository.findByDefinitionType(definitionTypeById);
    }

    /**
     * Delete a definition type detail by ID.
     */
    public void deleteDefinitionTypeDetail(Integer id) {
        if (!definitionTypeDetailRepository.existsById(id)) {
            throw new EntityNotFoundException("DefinitionTypeDetail with ID " + id + " not found.");
        }
        definitionTypeDetailRepository.deleteById(id);
    }

    public List<DefinitionTypes> searchDefinitionTypes(DefinitionTypeDto definitionTypeDto) {
        Specification<DefinitionTypes> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (definitionTypeDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), definitionTypeDto.getId()));
            }

            if (StringUtils.isNotBlank(definitionTypeDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + definitionTypeDto.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(definitionTypeDto.getDescription())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + definitionTypeDto.getDescription().toLowerCase() + "%"));
            }

            if (definitionTypeDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), definitionTypeDto.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return definitionTypeRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public List<DefinitionTypeDetails> searchDefinitionTypeDetails(DefinitionTypeDetailDto definitionTypeDetailDto) {
        Specification<DefinitionTypeDetails> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (definitionTypeDetailDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), definitionTypeDetailDto.getId()));
            }

            if (definitionTypeDetailDto.getDefinitionTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("definitionType").get("id"), definitionTypeDetailDto.getDefinitionTypeId()));
            }

            if (StringUtils.isNotBlank(definitionTypeDetailDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + definitionTypeDetailDto.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(definitionTypeDetailDto.getDescription())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + definitionTypeDetailDto.getDescription().toLowerCase() + "%"));
            }

            if (definitionTypeDetailDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), definitionTypeDetailDto.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return definitionTypeDetailRepository.findAll(specification, Pageable.unpaged()).getContent();
    }
}
