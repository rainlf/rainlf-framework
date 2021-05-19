package com.railf.framework.infrastructure.quartz.runner;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2021/5/6 15:52
 */
@Slf4j
public class QuartzRunner implements CommandLineRunner {
    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("定时任务容器启动...");
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("定时任务容器启动失败", e);
            throw e;

        }
    }
}
