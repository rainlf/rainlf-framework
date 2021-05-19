package com.railf.framework.infrastructure.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : rain
 * @date : 2020/6/17 21:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LRUCache {

    Class target() default Object.class;

    String key() default "default-key";

    boolean refresh() default false;
}
