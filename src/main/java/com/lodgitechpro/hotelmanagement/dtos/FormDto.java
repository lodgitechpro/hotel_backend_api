package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

@Data
public class FormDto {
    private Long id;
    private String name;
    private Long menuId;
    private String formName;
    private Integer position;
    private Boolean active;
    private String description;
    private String webUrl;
    private Boolean webEnabled;
    private Boolean desktopEnabled;
}
