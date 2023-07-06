package com.api.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.api.infrastructure.service.impl.DetectorServiceImpl.*(..))")
    private void callAtServiceImpl() {
    }

    @Before("callAtServiceImpl()")
    public void beforeServiceImplMethodAdvice(JoinPoint jp) {
        log.info(jp + ", args=[" + getArgs(jp) + "]");
    }

    @AfterThrowing("callAtServiceImpl()")
    public void afterThrowingServiceImplAdvice(JoinPoint jp) {
        log.error(jp + ", args=[" + getArgs(jp) + "]");
    }

    private String getArgs(JoinPoint jp) {
        return Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
