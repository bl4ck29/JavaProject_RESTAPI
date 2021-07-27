package com.market.skin.model.DTO;

import com.market.skin.model.RolesName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RolesDTO {
    private RolesName name;
    private int id;
}
