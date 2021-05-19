package com.railf.framework.infrastructure.mail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : rain
 * @date : 2021/5/6 13:47
 */
@Slf4j
public class MailSenderImpl implements MailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Set<String> reList, Set<String> ccList, String subject, String content) {
        reList = reList == null ? new HashSet<>(0) : reList;
        ccList = ccList == null ? new HashSet<>(0) : ccList;
        if (reList.isEmpty() && ccList.isEmpty()) {
            log.warn("no receiver and no cc, skip send email");
            return;
        }
        log.info("reList: {}, ccList: {}, subject: {}", reList, ccList, subject);

        String[] reArr = reList.stream().filter(StringUtils::hasText).toArray(String[]::new);
        String[] ccArr = ccList.stream().filter(StringUtils::hasText).toArray(String[]::new);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 发件人
            mimeMessageHelper.setFrom("rain@xxx.com");
            // 收件人
            mimeMessageHelper.setTo(reArr);
            // 抄送
            mimeMessageHelper.setCc(ccArr);
            // 密送
//            mimeMessageHelper.setBcc("bcc@xxx.com");
            // 主题
            mimeMessageHelper.setSubject(subject);
            // 正文
            mimeMessageHelper.setText(content);
            // 图片
//            mimeMessageHelper.addInline();
            // 附件
//            mimeMessageHelper.addAttachment();

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("send mail failed", e);
        }
    }
}
