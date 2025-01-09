package com.lodgitechpro.hotelmanagement.definationtype.dto;

import lombok.Data;

@Data
public class DefinitionTypeDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
}
