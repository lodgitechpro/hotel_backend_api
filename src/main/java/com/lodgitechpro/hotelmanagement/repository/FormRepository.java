package com.lodgitechpro.hotelmanagement.repository;


import com.lodgitechpro.hotelmanagement.entity.Form;
import com.lodgitechpro.hotelmanagement.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long>, JpaSpecificationExecutor<Form> {

    List<Form> findByMenu(Menu menu);

}
