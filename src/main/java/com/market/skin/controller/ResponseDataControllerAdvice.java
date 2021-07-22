package com.market.skin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@ControllerAdvice
public class ResponseDataControllerAdvice {
    private SimpleMappingExceptionResolver resolver;

    @Autowired
    public void setSimpleMappingeExceptionResolver(SimpleMappingExceptionResolver resolver){
        this.resolver = resolver;
    }

    @ModelAttribute("timestamp")
    public String getTimestamp(){
        return new Date().toString();
    }
}