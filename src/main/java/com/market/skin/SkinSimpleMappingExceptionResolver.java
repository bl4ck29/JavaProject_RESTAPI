package com.market.skin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class SkinSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{
    public SkinSimpleMappingExceptionResolver(){
        setWarnLogCategory(getClass().getName());
    }

    @Override
    public String buildLogMessage(Exception ex, HttpServletRequest request){
        return "MVC exception: " + ex.getLocalizedMessage();
    }

    @Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		ModelAndView mav = super.doResolveException(request, response, handler, exception);
		mav.addObject("url", request.getRequestURL());
		mav.addObject("timestamp", new Date());
		mav.addObject("status", 500);
		return mav;
	}
}
