package com.lm_api.librarymanagementapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ResultAspect {

    @AfterReturning(pointcut = "execution(* com.lm_api.librarymangementapi.service.*.*(..))",
            returning = "result")
    public void beforeMethod(JoinPoint joinPoint,Object result){
        log.info("Method {} returned {} ",joinPoint.getSignature().getName(),result);
    }
}
