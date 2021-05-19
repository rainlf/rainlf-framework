package com.railf.framework.infrastructure.mail;

import com.railf.framework.infrastructure.mail.service.MailSender;
import com.railf.framework.infrastructure.mail.service.MailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author : rain
 * @date : 2021/5/19 14:38
 */
@Configuration
@EnableConfigurationProperties(MailSenderProperties.class)
@ConditionalOnProperty(prefix = "framework.mail", value = "enable", havingValue = "true")
public class MailConfig {

    @Autowired
    private MailSenderProperties mailSenderProperties;

    @Bean
    @ConditionalOnMissingBean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailSenderProperties.getHost());
        javaMailSender.setPort(mailSenderProperties.getPort());
        javaMailSender.setUsername(mailSenderProperties.getUsername());
        javaMailSender.setPassword(mailSenderProperties.getPassword());

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", Boolean.TRUE);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", Boolean.TRUE);
        properties.put("mail.smtp.timeout", 60000);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    @Bean
    @ConditionalOnMissingBean
    public MailSender mailSender() {
        return new MailSenderImpl();
    }
}
