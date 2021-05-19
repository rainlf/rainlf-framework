package com.railf.framework.infrastructure.mail.service;

import java.util.Set;

/**
 * @author : rain
 * @date : 2021/5/6 13:45
 */
public interface MailSender {

    void sendEmail(Set<String> reList, Set<String> ccList, String subject, String content);
}
