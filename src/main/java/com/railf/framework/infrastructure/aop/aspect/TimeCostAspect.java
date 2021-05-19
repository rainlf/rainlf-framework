package com.railf.framework.infrastructure.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2020/6/9 12:14
 */
@Slf4j
@Aspect
@Component
public class TimeCostAspect {

    @Pointcut("@annotation(com.railf.framework.infrastructure.aop.annotation.TimeCost)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();
        log.info("{}.{}: start...", clazzName, methodName);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("{}.{}: end... cost time: {} ms", clazzName, methodName, end - start);
        return result;
    }
}
