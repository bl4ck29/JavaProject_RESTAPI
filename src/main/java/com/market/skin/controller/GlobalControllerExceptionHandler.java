package com.market.skin.controller;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.exception.ApplicationContextException;
import com.market.skin.exception.ConstraintViolationException;
import com.market.skin.exception.HttpMessageNotReadableException;
import com.market.skin.exception.IllegalStateException;
import com.market.skin.exception.InvalidFormatException;
import com.market.skin.exception.PSQLException;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.exception.TransactionSystemException;
import com.market.skin.model.ErrorCode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler({ApplicationContextException.class})
    public ResponseEntity<?> handlerApplicationContextException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse.builder().error(ErrorCode.ERR_SYSTEM).success(null).data(null).build());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(){
        return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(MessageResponse.builder().error(ErrorCode.ERR_DATA_CONFLICT).success(null).data(null).build());
    }

    @ExceptionHandler({IllegalStateException.class, InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleIllegalStateException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(MessageResponse.builder().error(ErrorCode.ERR_BAD_REQUEST).success(null).data(null).build());
    }

    @ExceptionHandler({PSQLException.class, TransactionSystemException.class})
    public ResponseEntity<?> handlePSQLException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse.builder().error(ErrorCode.ERR_SYSTEM).success(null).data(null).build());
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_NO_RECORD_FOUND).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.builder().error(ErrorCode.ERR_PARAM_MISSING)
        .url(request.getContextPath()).message(ex.getMessage()).build());
    }
}