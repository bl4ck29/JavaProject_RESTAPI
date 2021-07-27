package com.market.skin.controller.payload.response;
import com.market.skin.model.ErrorCode;
import com.market.skin.model.SuccessCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
   private ErrorCode error;
   private SuccessCode success;
   private String message;
   private String url;
   private Object data;
}