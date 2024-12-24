package com.lodgitechpro.hotelmanagement.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <T, D> T mapDtoToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }



}
