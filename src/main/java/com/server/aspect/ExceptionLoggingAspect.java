package com.server.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "@annotation(com.server.annotation.LogException)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toString();
        System.err.println("Exception in method " + methodName + ": " + ex.getMessage());
    }
}
