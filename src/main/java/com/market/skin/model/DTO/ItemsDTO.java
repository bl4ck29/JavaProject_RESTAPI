package com.market.skin.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemsDTO {
    private int id;
    private String gun;
    private String creator_id;
    private String pattern;
    private String image;
    private float price;
}
