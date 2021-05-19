package com.railf.framework.adapter.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : rain
 * @date : 2021/5/18 17:52
 */
@Slf4j
@RestController
public class HealthApi {

    @GetMapping("health")
    public String health() {
        log.info("ok");
        return "ok";
    }
}
