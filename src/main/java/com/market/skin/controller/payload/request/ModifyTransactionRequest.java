package com.market.skin.controller.payload.request;

import com.market.skin.model.Transactions;
import com.market.skin.model.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ModifyTransactionRequest {
    private Transactions trans;
    private Users user;
}
