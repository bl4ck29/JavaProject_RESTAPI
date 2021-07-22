package com.market.skin.controller.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class MessageResponse {
    private LocalDateTime timestamp;
    private String url;
    private String message;
    private String statusCode;

    public MessageResponse(String statusCode, String message, String url){
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
        this.url = url;
        this.message = message;
    }

    public MessageResponse(String message){
        this.message = message;
    }
}