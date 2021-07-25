package com.market.skin;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;



@Configuration
public class ExceptionConfiguration {
    protected Logger logger;
    public ExceptionConfiguration(){
        logger = LoggerFactory.getLogger(getClass());
        logger.info("Creating ExceptionConfiguration");
    }

    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimMappingExceptionResolver(){
        logger.info("Create SimpleMappingExceptionResolver");
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        Properties mapping = new Properties();
        mapping.setProperty("DatabaseException", "databaseException");
        mapping.setProperty("ConstraintViolationException", "constraintViolation");

        r.setExceptionMappings(mapping);
		r.setExceptionAttribute("ex");
        r.setDefaultErrorView("defaultErrorPage");
        return r;
    }
}
