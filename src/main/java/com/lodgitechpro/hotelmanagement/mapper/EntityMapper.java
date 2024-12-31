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

    /**
     * Maps updates from a DTO to an existing entity instance.
     *
     * @param dto         The DTO containing the updated values.
     * @param entity      The existing entity instance to update.
     * @param <D>         The type of the DTO.
     * @param <T>         The type of the entity.
     */
    public <D, T> void mapDtoToEntity(D dto, T entity) {
        modelMapper.map(dto, entity);
    }
}
