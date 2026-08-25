package com.lm_api.librarymangementapi.aspect;

import com.lm_api.librarymangementapi.annotation.TrackExecution;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(trackExecution)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, TrackExecution trackExecution) throws Throwable{
        long startTime=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long endTime=System.currentTimeMillis();
        long executionTime=endTime-startTime;
        log.info(" {} executed in {} ms",trackExecution.name(),executionTime);
        return result;
    }
}
