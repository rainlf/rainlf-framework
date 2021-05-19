package com.railf.framework.application.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2020/9/23 12:28
 */
@Component
@EnableScheduling
public class ScheduledJob {
//    @Scheduled(cron = "0/5 * * * * *")
//    public void test() {
//        System.out.println("我是每5s一次的信息");
//    }
//
//    @Scheduled(fixedDelay = 5 * 1000)
//    public void test2() {
//        System.out.println("我是上次任务完成后，过5s再执行的信息");
//    }
//
//    @Scheduled(fixedRate = 5 * 1000)
//    public void test3() {
//        System.out.println("我是上次任务开始时，过5s再执行的信息");
//    }
}
