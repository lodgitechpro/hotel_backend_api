package com.lodgitechpro.hotelmanagement.definationtype.repository;


import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinitionTypesRepository extends JpaRepository<DefinitionTypes, Integer>, JpaSpecificationExecutor<DefinitionTypes> {
}
