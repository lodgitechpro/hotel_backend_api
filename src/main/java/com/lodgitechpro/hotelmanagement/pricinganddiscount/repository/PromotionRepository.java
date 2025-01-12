package com.lodgitechpro.hotelmanagement.pricinganddiscount.repository;


import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotions, Integer>, JpaSpecificationExecutor<Promotions> {
    List<Promotions> findByActive(Boolean active);
    Optional<Promotions> findByPromotionCodeAndActive(String promotionCode, Boolean active);

}
