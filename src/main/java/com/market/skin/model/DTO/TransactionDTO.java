package com.market.skin.model.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TransactionDTO {
    private String username;
    private List<ItemsDTO> lstItemName;
    private LocalDateTime update_time;
    private String status;
}
