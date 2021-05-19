package com.railf.framework.application.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2021/5/19 13:17
 */
@Slf4j
@Component
public class BeanStatusRunner implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = applicationContext.getType(beanName);
            log.info("BeanName: {}", beanName);
            log.info("beanType: {}", beanType);
            log.info("----------------------------------------------------------------------");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
