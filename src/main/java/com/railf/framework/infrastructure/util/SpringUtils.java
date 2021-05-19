package com.railf.framework.infrastructure.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2020/6/16 12:27
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public static String getEnv() {
        return ctx.getEnvironment().getActiveProfiles()[0];
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
