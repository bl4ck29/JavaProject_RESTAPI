package com.market.skin.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UsersDTO {
    private String username;
    private String login_type;
    private String email;
    private String profile;
}
