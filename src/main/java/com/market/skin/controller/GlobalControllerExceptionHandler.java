package com.market.skin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.ConstraintViolationException;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.exception.ApiError;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler({Exception.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception{
        if(AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) throw ex;

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestPartException ex, HttpHeaders header, HttpStatus status, WebRequest request){
        String error = ex.getRequestPartName() + "parameter is missing";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handlerDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationHandler(HttpServletRequest request, Exception ex) throws Exception{
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new MessageResponse("Constraint violation"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({TransactionSystemException.class})
    public ModelAndView transactionSystemHandler(HttpServletRequest request, Exception ex) throws Exception{
        ModelAndView mav = new ModelAndView();
        mav.setViewName(ex.getLocalizedMessage());
        return mav;
    }
}
