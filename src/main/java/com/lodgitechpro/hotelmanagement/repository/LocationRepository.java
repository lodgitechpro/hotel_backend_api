package com.lodgitechpro.hotelmanagement.repository;

import com.lodgitechpro.hotelmanagement.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByOrgId(long orgId);

}
