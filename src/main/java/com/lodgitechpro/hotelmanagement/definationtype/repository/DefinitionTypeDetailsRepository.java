package com.lodgitechpro.hotelmanagement.definationtype.repository;


import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypeDetails;
import com.lodgitechpro.hotelmanagement.definationtype.entity.DefinitionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefinitionTypeDetailsRepository extends JpaRepository<DefinitionTypeDetails, Integer>, JpaSpecificationExecutor<DefinitionTypeDetails> {
    List<DefinitionTypeDetails> findByDefinitionType(DefinitionTypes definitionTypes);
}
