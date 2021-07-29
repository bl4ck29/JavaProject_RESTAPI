package com.market.skin.controller.payload.request;

import com.market.skin.model.Items;
import com.market.skin.model.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ModifyItemRequest {
    private Items item;
    private Users user;
}
