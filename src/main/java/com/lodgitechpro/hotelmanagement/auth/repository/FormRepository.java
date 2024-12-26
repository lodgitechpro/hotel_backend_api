package com.lodgitechpro.hotelmanagement.auth.repository;


import com.lodgitechpro.hotelmanagement.auth.entity.Form;
import com.lodgitechpro.hotelmanagement.auth.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer>, JpaSpecificationExecutor<Form> {

    List<Form> findByMenu(Menu menu);

}
