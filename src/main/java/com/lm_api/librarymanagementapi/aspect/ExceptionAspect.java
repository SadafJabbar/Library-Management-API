package com.lm_api.librarymanagementapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExceptionAspect {


    @AfterThrowing(pointcut = "execution (* com.lm_api.librarymangementapi.service.*.*(..))",
    throwing = "exception")
    public void afterThrowingMethod(JoinPoint joinPoint,Throwable exception){
        log.error("Exception in Method {}: {} ",joinPoint.getSignature().getName(),exception.getMessage());
    }
}
