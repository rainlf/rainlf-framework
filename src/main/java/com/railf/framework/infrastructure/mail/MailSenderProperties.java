package com.railf.framework.infrastructure.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : rain
 * @date : 2021/5/6 13:43
 */
@Data
@ConfigurationProperties(prefix = "framework.mail")
public class MailSenderProperties {
    private Boolean enable = Boolean.FALSE;
    private String host;
    private Integer port;
    private String username;
    private String password;
}
