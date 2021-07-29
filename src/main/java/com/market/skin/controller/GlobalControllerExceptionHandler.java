package com.market.skin.controller;

import javax.validation.ConstraintViolationException;

import com.market.skin.controller.payload.response.MessageResponse;

import org.postgresql.util.PSQLException;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.ErrorCode;

import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import java.lang.NumberFormatException;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler({ ApplicationContextException.class })
    public ResponseEntity<?> handlerApplicationContextException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse.builder().error(ErrorCode.ERR_SYSTEM).success(null).data(null).build());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(MessageResponse.builder().error(ErrorCode.ERR_DATA_CONFLICT).message(ex.getMessage()).build());
    }

    @ExceptionHandler({ PSQLException.class, TransactionSystemException.class })
    public ResponseEntity<?> handlePSQLException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse.builder().error(ErrorCode.ERR_SYSTEM).build());
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_NO_RECORD_FOUND).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }

    @ExceptionHandler({Unauthorized.class})
    public ResponseEntity<MessageResponse> handleUnauthorizedException(Unauthorized ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_UNAUTHORIZED).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<MessageResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_DUPLICATE).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }

    @ExceptionHandler({PropertyReferenceException.class})
    public ResponseEntity<MessageResponse> handlePropertyReferenceException(PropertyReferenceException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_DUPLICATE).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<MessageResponse> handleNumberFormatException(NumberFormatException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(MessageResponse.builder().error(ErrorCode.ERR_DUPLICATE).success(null).message(ex.getMessage()).data(null).url(request.getContextPath()).build());
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.builder().error(ErrorCode.ERR_PARAM_MISSING)
        .url(request.getContextPath()).message(ex.getMessage()).build());
    }
}