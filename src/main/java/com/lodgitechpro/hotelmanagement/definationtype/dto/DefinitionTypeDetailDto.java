package com.lodgitechpro.hotelmanagement.definationtype.dto;

import lombok.Data;

@Data
public class DefinitionTypeDetailDto {
    private Integer id;
    private Integer definitionTypeId;
    private String name;
    private String description;
    private Boolean active;
}
