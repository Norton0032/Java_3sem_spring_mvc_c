package com.example.java_3sem_spring_mvc.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ExecutionTimeLoggingAspect {
    @Pointcut("within(com.example.java_3sem_spring_mvc.services.*)")
    public void allServiceMethods() {}

    @Around("allServiceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Метод {} выполняется: {} ms", joinPoint.getSignature().toShortString(), endTime - startTime);
        return proceed;
    }
}
