package com.market.skin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

public class SkinExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver{
    public SkinExceptionHandlerExceptionResolver(){
        setWarnLogCategory(getClass().getName());
        setOrder(LOWEST_PRECEDENCE - 1);
    }

    @Override
    public String buildLogMessage(Exception ex, HttpServletRequest request){
        return "MVC exception: " + ex.getLocalizedMessage();
    }

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex){
        ModelAndView mav = super.doResolveHandlerMethodException(request, response, handlerMethod, ex);
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.addObject("status", 500);
        return mav;
    }
    
}
