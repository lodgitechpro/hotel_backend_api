package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

@Data
public class FormDto {
    private Integer id;
    private String name;
    private Integer menuId;
    private String formName;
    private Integer position;
    private Boolean active;
    private String description;
    private String webUrl;
    private Boolean webEnabled;
    private Boolean desktopEnabled;
}
