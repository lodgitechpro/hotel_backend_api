package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

@Data
public class MenuDto {
    private Long id;
    private String name;
    private Integer position;
    private Boolean active;
    private String menuName;
    private String mnemonic;
}
