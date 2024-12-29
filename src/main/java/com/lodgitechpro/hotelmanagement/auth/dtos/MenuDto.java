package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

@Data
public class MenuDto {
    private Integer id;
    private String name;
    private Integer position;
    private Boolean active;
    private String menuName;
    private String mnemonic;
}
