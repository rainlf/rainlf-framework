package com.railf.framework.application.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author : rain
 * @date : 2021/5/19 15:47
 */
@Slf4j
@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("Test begin");
        log.info("Test end");
    }
}
