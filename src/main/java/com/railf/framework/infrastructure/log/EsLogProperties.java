package com.railf.framework.infrastructure.log;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : rain
 * @date : 2021/4/30 17:45
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "framework.log.es")
public class EsLogProperties {
    private String schema = "http";
    private String host;
    private Integer port = 80;
    private String username;
    private String password;
    private String indexName;
}
