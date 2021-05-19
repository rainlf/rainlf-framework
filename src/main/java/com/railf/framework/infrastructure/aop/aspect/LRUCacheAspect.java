package com.railf.framework.infrastructure.aop.aspect;

import com.railf.framework.infrastructure.aop.annotation.LRUCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * @author : rain
 * @date : 2020/6/9 12:14
 */
@Slf4j
@Aspect
@Component
public class LRUCacheAspect {

    @Autowired
    private com.railf.framework.infrastructure.aop.cache.LRUCache lruCache;

    @Pointcut("@annotation(com.railf.framework.infrastructure.aop.annotation.LRUCache)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String clazzName = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LRUCache.class).target();
        String methodKey = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LRUCache.class).key();
        boolean refresh = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LRUCache.class).refresh();

        Object result;
        String key = DigestUtils.md5DigestAsHex((clazzName + methodKey + Arrays.toString(args)).getBytes());
        if (!refresh && lruCache.get(key) != null) {
            result = lruCache.get(key);
        } else {
            result = joinPoint.proceed();
            lruCache.put(key, result);
        }

        return result;
    }
}
